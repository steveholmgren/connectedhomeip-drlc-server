/*
 *
 *    Copyright (c) 2023 Project CHIP Authors
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

package matter.controller.cluster.clusters

import java.util.logging.Level
import java.util.logging.Logger
import java.time.Duration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform
import matter.controller.MatterController
import matter.controller.ReadRequest
import matter.controller.ReadData
import matter.controller.ReadFailure
import matter.controller.ReadResponse
import matter.controller.SubscribeRequest
import matter.controller.SubscriptionState
import matter.controller.ByteSubscriptionState
import matter.controller.ShortSubscriptionState
import matter.controller.IntSubscriptionState
import matter.controller.LongSubscriptionState
import matter.controller.FloatSubscriptionState
import matter.controller.DoubleSubscriptionState
import matter.controller.CharSubscriptionState
import matter.controller.BooleanSubscriptionState
import matter.controller.UByteSubscriptionState
import matter.controller.UShortSubscriptionState
import matter.controller.UIntSubscriptionState
import matter.controller.ULongSubscriptionState
import matter.controller.StringSubscriptionState
import matter.controller.ByteArraySubscriptionState
import matter.controller.WriteRequest
import matter.controller.WriteRequests
import matter.controller.WriteResponse
import matter.controller.AttributeWriteError
import matter.controller.InvokeRequest
import matter.controller.InvokeResponse
import matter.controller.model.AttributePath
import matter.controller.model.CommandPath
import matter.controller.cluster.structs.*
import matter.tlv.AnonymousTag
import matter.tlv.ContextSpecificTag
import matter.tlv.Tag
import matter.tlv.TlvParsingException
import matter.tlv.TlvReader
import matter.tlv.TlvWriter

class TimerCluster(private val controller: MatterController, private val endpointId: UShort) {class GeneratedCommandListAttribute(
    val value: List<UInt>
  )

  sealed class GeneratedCommandListAttributeSubscriptionState {
    data class Success(
    val value: List<UInt>
    ) : GeneratedCommandListAttributeSubscriptionState()
    
    data class Error(val exception: Exception) : GeneratedCommandListAttributeSubscriptionState()

    object SubscriptionEstablished : GeneratedCommandListAttributeSubscriptionState()    
  }  
class AcceptedCommandListAttribute(
    val value: List<UInt>
  )

  sealed class AcceptedCommandListAttributeSubscriptionState {
    data class Success(
    val value: List<UInt>
    ) : AcceptedCommandListAttributeSubscriptionState()
    
    data class Error(val exception: Exception) : AcceptedCommandListAttributeSubscriptionState()

    object SubscriptionEstablished : AcceptedCommandListAttributeSubscriptionState()    
  }  
class EventListAttribute(
    val value: List<UInt>
  )

  sealed class EventListAttributeSubscriptionState {
    data class Success(
    val value: List<UInt>
    ) : EventListAttributeSubscriptionState()
    
    data class Error(val exception: Exception) : EventListAttributeSubscriptionState()

    object SubscriptionEstablished : EventListAttributeSubscriptionState()    
  }  
class AttributeListAttribute(
    val value: List<UInt>
  )

  sealed class AttributeListAttributeSubscriptionState {
    data class Success(
    val value: List<UInt>
    ) : AttributeListAttributeSubscriptionState()
    
    data class Error(val exception: Exception) : AttributeListAttributeSubscriptionState()

    object SubscriptionEstablished : AttributeListAttributeSubscriptionState()    
  }  

  suspend fun setTimer(newTime: UInt
      ,timedInvokeTimeout: Duration? = null) {
    val commandId: UInt = 0u

    val tlvWriter = TlvWriter()
    tlvWriter.startStructure(AnonymousTag)

    val TAG_NEW_TIME_REQ: Int = 0
    tlvWriter.put(ContextSpecificTag(TAG_NEW_TIME_REQ), newTime)    
    tlvWriter.endStructure()

    val request: InvokeRequest =
      InvokeRequest(
        CommandPath(endpointId, clusterId = CLUSTER_ID, commandId),
        tlvPayload = tlvWriter.getEncoded(),
        timedRequest = timedInvokeTimeout
      )

    val response: InvokeResponse = controller.invoke(request)
    logger.log(Level.FINE, "Invoke command succeeded: ${response}")
  }

  suspend fun resetTimer(timedInvokeTimeout: Duration? = null) {
    val commandId: UInt = 1u

    val tlvWriter = TlvWriter()
    tlvWriter.startStructure(AnonymousTag)    
    tlvWriter.endStructure()

    val request: InvokeRequest =
      InvokeRequest(
        CommandPath(endpointId, clusterId = CLUSTER_ID, commandId),
        tlvPayload = tlvWriter.getEncoded(),
        timedRequest = timedInvokeTimeout
      )

    val response: InvokeResponse = controller.invoke(request)
    logger.log(Level.FINE, "Invoke command succeeded: ${response}")
  }

  suspend fun addTime(additionalTime: UInt
      ,timedInvokeTimeout: Duration? = null) {
    val commandId: UInt = 2u

    val tlvWriter = TlvWriter()
    tlvWriter.startStructure(AnonymousTag)

    val TAG_ADDITIONAL_TIME_REQ: Int = 0
    tlvWriter.put(ContextSpecificTag(TAG_ADDITIONAL_TIME_REQ), additionalTime)    
    tlvWriter.endStructure()

    val request: InvokeRequest =
      InvokeRequest(
        CommandPath(endpointId, clusterId = CLUSTER_ID, commandId),
        tlvPayload = tlvWriter.getEncoded(),
        timedRequest = timedInvokeTimeout
      )

    val response: InvokeResponse = controller.invoke(request)
    logger.log(Level.FINE, "Invoke command succeeded: ${response}")
  }

  suspend fun reduceTime(timeReduction: UInt
      ,timedInvokeTimeout: Duration? = null) {
    val commandId: UInt = 3u

    val tlvWriter = TlvWriter()
    tlvWriter.startStructure(AnonymousTag)

    val TAG_TIME_REDUCTION_REQ: Int = 0
    tlvWriter.put(ContextSpecificTag(TAG_TIME_REDUCTION_REQ), timeReduction)    
    tlvWriter.endStructure()

    val request: InvokeRequest =
      InvokeRequest(
        CommandPath(endpointId, clusterId = CLUSTER_ID, commandId),
        tlvPayload = tlvWriter.getEncoded(),
        timedRequest = timedInvokeTimeout
      )

    val response: InvokeResponse = controller.invoke(request)
    logger.log(Level.FINE, "Invoke command succeeded: ${response}")
  }
suspend fun readSetTimeAttribute(): UInt {val ATTRIBUTE_ID: UInt = 0u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Settime attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)


    return decodedValue
  }

  suspend fun subscribeSetTimeAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<UIntSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 0u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(UIntSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Settime attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)

          emit(UIntSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(UIntSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readTimeRemainingAttribute(): UInt {val ATTRIBUTE_ID: UInt = 1u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Timeremaining attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)


    return decodedValue
  }

  suspend fun subscribeTimeRemainingAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<UIntSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 1u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(UIntSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Timeremaining attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)

          emit(UIntSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(UIntSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readTimerStateAttribute(): UByte {val ATTRIBUTE_ID: UInt = 2u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Timerstate attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: UByte = tlvReader.getUByte(AnonymousTag)


    return decodedValue
  }

  suspend fun subscribeTimerStateAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<UByteSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 2u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(UByteSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Timerstate attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: UByte = tlvReader.getUByte(AnonymousTag)

          emit(UByteSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(UByteSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readGeneratedCommandListAttribute(): GeneratedCommandListAttribute {val ATTRIBUTE_ID: UInt = 65528u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Generatedcommandlist attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }


    return GeneratedCommandListAttribute(decodedValue)
  }

  suspend fun subscribeGeneratedCommandListAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<GeneratedCommandListAttributeSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65528u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(GeneratedCommandListAttributeSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Generatedcommandlist attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }

          emit(GeneratedCommandListAttributeSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(GeneratedCommandListAttributeSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readAcceptedCommandListAttribute(): AcceptedCommandListAttribute {val ATTRIBUTE_ID: UInt = 65529u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Acceptedcommandlist attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }


    return AcceptedCommandListAttribute(decodedValue)
  }

  suspend fun subscribeAcceptedCommandListAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<AcceptedCommandListAttributeSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65529u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(AcceptedCommandListAttributeSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Acceptedcommandlist attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }

          emit(AcceptedCommandListAttributeSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(AcceptedCommandListAttributeSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readEventListAttribute(): EventListAttribute {val ATTRIBUTE_ID: UInt = 65530u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Eventlist attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }


    return EventListAttribute(decodedValue)
  }

  suspend fun subscribeEventListAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<EventListAttributeSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65530u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(EventListAttributeSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Eventlist attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }

          emit(EventListAttributeSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(EventListAttributeSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readAttributeListAttribute(): AttributeListAttribute {val ATTRIBUTE_ID: UInt = 65531u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Attributelist attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }


    return AttributeListAttribute(decodedValue)
  }

  suspend fun subscribeAttributeListAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<AttributeListAttributeSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65531u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(AttributeListAttributeSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Attributelist attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: List<UInt> = buildList<UInt> {
      tlvReader.enterArray(AnonymousTag)
      while(!tlvReader.isEndOfContainer()) {
        add(tlvReader.getUInt(AnonymousTag))
      }
      tlvReader.exitContainer()
    }

          emit(AttributeListAttributeSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(AttributeListAttributeSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readFeatureMapAttribute(): UInt {val ATTRIBUTE_ID: UInt = 65532u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Featuremap attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)


    return decodedValue
  }

  suspend fun subscribeFeatureMapAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<UIntSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65532u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(UIntSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Featuremap attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: UInt = tlvReader.getUInt(AnonymousTag)

          emit(UIntSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(UIntSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }
suspend fun readClusterRevisionAttribute(): UShort {val ATTRIBUTE_ID: UInt = 65533u

    val attributePath = AttributePath(
      endpointId = endpointId, 
      clusterId = CLUSTER_ID,
      attributeId = ATTRIBUTE_ID
    )

    val readRequest = ReadRequest(
      eventPaths = emptyList(),
      attributePaths = listOf(attributePath)
    )
    
    val response = controller.read(readRequest)

    if (response.successes.isEmpty()) {
      logger.log(Level.WARNING, "Read command failed")
      throw IllegalStateException("Read command failed with failures: ${response.failures}")     
    }    

    logger.log(Level.FINE, "Read command succeeded")

    val attributeData =
      response.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
        it.path.attributeId == ATTRIBUTE_ID
      }        
       
    requireNotNull(attributeData) { 
      "Clusterrevision attribute not found in response" 
    }

    // Decode the TLV data into the appropriate type
    val tlvReader = TlvReader(attributeData.data)
    val decodedValue: UShort = tlvReader.getUShort(AnonymousTag)


    return decodedValue
  }

  suspend fun subscribeClusterRevisionAttribute(
    minInterval: Int,
    maxInterval: Int
  ): Flow<UShortSubscriptionState> {
    val ATTRIBUTE_ID: UInt = 65533u
    val attributePaths = listOf(
      AttributePath(
          endpointId = endpointId,
          clusterId = CLUSTER_ID,
          attributeId = ATTRIBUTE_ID
      )
    )

    val subscribeRequest: SubscribeRequest = SubscribeRequest(
      eventPaths = emptyList(), 
      attributePaths = attributePaths, 
      minInterval = Duration.ofSeconds(minInterval.toLong()), 
      maxInterval = Duration.ofSeconds(maxInterval.toLong())
    )

    return controller.subscribe(subscribeRequest).transform { subscriptionState ->
      when (subscriptionState) {
        is SubscriptionState.SubscriptionErrorNotification -> {
          emit(UShortSubscriptionState.Error(Exception("Subscription terminated with error code: ${subscriptionState.terminationCause}")))
        }
        is SubscriptionState.NodeStateUpdate -> {
          val attributeData =
            subscriptionState.updateState.successes.filterIsInstance<ReadData.Attribute>().firstOrNull {
              it.path.attributeId == ATTRIBUTE_ID
            }        
             
          requireNotNull(attributeData) { 
            "Clusterrevision attribute not found in Node State update" 
          }

          // Decode the TLV data into the appropriate type
          val tlvReader = TlvReader(attributeData.data)
          val decodedValue: UShort = tlvReader.getUShort(AnonymousTag)

          emit(UShortSubscriptionState.Success(decodedValue))
        }
        SubscriptionState.SubscriptionEstablished -> {
          emit(UShortSubscriptionState.SubscriptionEstablished)
        }
      }
    }    
  }

  companion object {
    private val logger = Logger.getLogger(TimerCluster::class.java.name)
    const val CLUSTER_ID: UInt = 71u
  }
}
