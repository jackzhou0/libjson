package org.chinagcd.libjson.factory

import org.chinagcd.libjson.dispatcher.Serializer
import org.chinagcd.libjson.enums.SerializeFeature
import org.chinagcd.libjson.enums.SerializerType
import org.chinagcd.libjson.serializer.ListSerializer
import org.chinagcd.libjson.serializer.MapSerializer
import org.chinagcd.libjson.serializer.ObjectSerializer

object SerializerFactory {

    fun fetchSerializer(type: SerializerType, feature: SerializeFeature): Serializer {
        return when(type) {
            SerializerType.LIST -> ListSerializer(feature)
            SerializerType.MAP -> MapSerializer(feature)
            SerializerType.OBJECT -> ObjectSerializer(feature)
        }
    }
}