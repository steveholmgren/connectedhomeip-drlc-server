/*
 *
 *    Copyright (c) 2022 Project CHIP Authors
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

// module headers
#import <Matter/Matter.h>

#import "MTRErrorTestUtils.h"
#import "MTRTestKeys.h"
#import "MTRTestResetCommissioneeHelper.h"
#import "MTRTestStorage.h"

// system dependencies
#import <XCTest/XCTest.h>

static const uint16_t kPairingTimeoutInSeconds = 10;
static const uint16_t kTimeoutInSeconds = 3;
static uint64_t sDeviceId = 0x12344321;
static NSString * kOnboardingPayload = @"MT:Y.K90SO527JA0648G00";
static const uint16_t kLocalPort = 5541;
static const uint16_t kTestVendorId = 0xFFF1u;

// Singleton controller we use.
static MTRDeviceController * sController = nil;

// Keys we can use to restart the controller.
static MTRTestKeys * sTestKeys = nil;

// A no-op MTRDeviceAttestationDelegate which lets us test (by default, in CI)
// commissioning flows that have such a delegate.
@interface NoOpAttestationDelegate : NSObject <MTRDeviceAttestationDelegate>
@property (nonatomic) XCTestExpectation * expectation;

- (instancetype)initWithExpectation:(XCTestExpectation *)expectation;
@end

@implementation NoOpAttestationDelegate
- (instancetype)initWithExpectation:(XCTestExpectation *)expectation
{
    if (!(self = [super init])) {
        return nil;
    }

    _expectation = expectation;
    return self;
}

- (void)deviceAttestationCompletedForController:(MTRDeviceController *)controller
                             opaqueDeviceHandle:(void *)opaqueDeviceHandle
                          attestationDeviceInfo:(MTRDeviceAttestationDeviceInfo *)attestationDeviceInfo
                                          error:(NSError * _Nullable)error
{
    [self.expectation fulfill];
    // Hard-coded to what our example server app uses for now.
    XCTAssertEqualObjects(attestationDeviceInfo.vendorID, @(0xFFF2));
    XCTAssertEqualObjects(attestationDeviceInfo.productID, @(0x8001));
    XCTAssertEqualObjects(attestationDeviceInfo.basicInformationVendorID, @(0xFFF1));
    XCTAssertEqualObjects(attestationDeviceInfo.basicInformationProductID, @(0x8000));
    [controller continueCommissioningDevice:opaqueDeviceHandle ignoreAttestationFailure:NO error:nil];
}

@end

@interface MTRPairingTestControllerDelegate : NSObject <MTRDeviceControllerDelegate>
@property (nonatomic, strong) XCTestExpectation * expectation;
@property (nonatomic, nullable) id<MTRDeviceAttestationDelegate> attestationDelegate;
@property (nonatomic, nullable) NSNumber * failSafeExtension;
@end

@implementation MTRPairingTestControllerDelegate
- (id)initWithExpectation:(XCTestExpectation *)expectation
      attestationDelegate:(id<MTRDeviceAttestationDelegate>)attestationDelegate
        failSafeExtension:(NSNumber *)failSafeExtension
{
    self = [super init];
    if (self) {
        _expectation = expectation;
        _attestationDelegate = attestationDelegate;
        _failSafeExtension = failSafeExtension;
    }
    return self;
}

- (void)controller:(MTRDeviceController *)controller commissioningSessionEstablishmentDone:(NSError * _Nullable)error
{
    XCTAssertEqual(error.code, 0);

    __auto_type * params = [[MTRCommissioningParameters alloc] init];
    params.deviceAttestationDelegate = self.attestationDelegate;
    params.failSafeTimeout = self.failSafeExtension;

    NSError * commissionError = nil;
    [controller commissionNodeWithID:@(sDeviceId) commissioningParams:params error:&commissionError];
    XCTAssertNil(commissionError);

    // Keep waiting for onCommissioningComplete
}

- (void)controller:(MTRDeviceController *)controller commissioningComplete:(NSError * _Nullable)error
{
    XCTAssertEqual(error.code, 0);
    [_expectation fulfill];
    _expectation = nil;
}

@end

// attestationDelegate and failSafeExtension can both be nil
static void DoPairingTest(XCTestCase * testcase, id<MTRDeviceAttestationDelegate> attestationDelegate, NSNumber * failSafeExtension)
{
    // Don't reuse node ids, because that will confuse us.
    ++sDeviceId;
    XCTestExpectation * expectation = [testcase expectationWithDescription:@"Commissioning Complete"];
    __auto_type * controller = sController;

    __auto_type * controllerDelegate = [[MTRPairingTestControllerDelegate alloc] initWithExpectation:expectation
                                                                                 attestationDelegate:attestationDelegate
                                                                                   failSafeExtension:failSafeExtension];
    dispatch_queue_t callbackQueue = dispatch_queue_create("com.chip.pairing", DISPATCH_QUEUE_SERIAL);

    [controller setDeviceControllerDelegate:controllerDelegate queue:callbackQueue];

    NSError * error;
    __auto_type * payload = [MTRSetupPayload setupPayloadWithOnboardingPayload:kOnboardingPayload error:&error];
    XCTAssertNotNil(payload);
    XCTAssertNil(error);

    [controller setupCommissioningSessionWithPayload:payload newNodeID:@(sDeviceId) error:&error];
    XCTAssertNil(error);

    [testcase waitForExpectations:@[ expectation ] timeout:kPairingTimeoutInSeconds];

    ResetCommissionee([MTRBaseDevice deviceWithNodeID:@(sDeviceId) controller:controller], dispatch_get_main_queue(), testcase,
        kTimeoutInSeconds);
}

@interface MTRPairingTests : XCTestCase
@end

static BOOL sStackInitRan = NO;
static BOOL sNeedsStackShutdown = YES;

@implementation MTRPairingTests

+ (void)tearDown
{
    // Global teardown, runs once
    if (sNeedsStackShutdown) {
        // We don't need to worry about ResetCommissionee.  If we get here,
        // we're running only one of our test methods (using
        // -only-testing:MatterTests/MTROTAProviderTests/testMethodName), since
        // we did not run test999_TearDown.
        [self shutdownStack];
    }
}

- (void)setUp
{
    // Per-test setup, runs before each test.
    [super setUp];
    [self setContinueAfterFailure:NO];

    if (sStackInitRan == NO) {
        [self initStack];
    }
}

- (void)tearDown
{
    // Per-test teardown, runs after each test.
    [super tearDown];
}

- (void)initStack
{
    sStackInitRan = YES;

    __auto_type * factory = [MTRDeviceControllerFactory sharedInstance];
    XCTAssertNotNil(factory);

    __auto_type * storage = [[MTRTestStorage alloc] init];
    __auto_type * factoryParams = [[MTRDeviceControllerFactoryParams alloc] initWithStorage:storage];
    factoryParams.port = @(kLocalPort);

    BOOL ok = [factory startControllerFactory:factoryParams error:nil];
    XCTAssertTrue(ok);

    __auto_type * testKeys = [[MTRTestKeys alloc] init];
    XCTAssertNotNil(testKeys);

    sTestKeys = testKeys;

    // Needs to match what startControllerOnExistingFabric calls elsewhere in
    // this file do.
    __auto_type * params = [[MTRDeviceControllerStartupParams alloc] initWithIPK:testKeys.ipk fabricID:@(1) nocSigner:testKeys];
    params.vendorID = @(kTestVendorId);

    MTRDeviceController * controller = [factory createControllerOnNewFabric:params error:nil];
    XCTAssertNotNil(controller);

    sController = controller;
}

+ (void)shutdownStack
{
    sNeedsStackShutdown = NO;

    MTRDeviceController * controller = sController;
    XCTAssertNotNil(controller);

    [controller shutdown];
    XCTAssertFalse([controller isRunning]);

    [[MTRDeviceControllerFactory sharedInstance] stopControllerFactory];
}

- (void)test000_SetUp
{
    // Nothing to do here; our setUp method handled this already.  This test
    // just exists to make the setup not look like it's happening inside other
    // tests.
}

- (void)test001_PairWithoutAttestationDelegate
{
    DoPairingTest(self, nil, nil);
}

- (void)test002_PairWithAttestationDelegateNoFailsafeExtension
{
    XCTestExpectation * expectation = [self expectationWithDescription:@"Attestation delegate called"];

    DoPairingTest(self, [[NoOpAttestationDelegate alloc] initWithExpectation:expectation], nil);

    [self waitForExpectations:@[ expectation ] timeout:kTimeoutInSeconds];
}

- (void)test003_PairWithAttestationDelegateFailsafeExtensionShort
{
    XCTestExpectation * expectation = [self expectationWithDescription:@"Attestation delegate called"];

    // Extend by a time that is going to be smaller than the 60s default we
    // already have set via CHIP_DEVICE_CONFIG_FAILSAFE_EXPIRY_LENGTH_SEC on the
    // server side, minus whatever time that has likely passed.
    DoPairingTest(self, [[NoOpAttestationDelegate alloc] initWithExpectation:expectation], @(30));

    [self waitForExpectations:@[ expectation ] timeout:kTimeoutInSeconds];
}

- (void)test004_PairWithAttestationDelegateFailsafeExtensionLong
{
    XCTestExpectation * expectation = [self expectationWithDescription:@"Attestation delegate called"];

    // Extend by a time that is going to be larger than the 60s default we
    // already have set via CHIP_DEVICE_CONFIG_FAILSAFE_EXPIRY_LENGTH_SEC on the
    // server side.
    DoPairingTest(self, [[NoOpAttestationDelegate alloc] initWithExpectation:expectation], @(90));

    [self waitForExpectations:@[ expectation ] timeout:kTimeoutInSeconds];
}

- (void)test999_TearDown
{
    [[self class] shutdownStack];
}

@end
