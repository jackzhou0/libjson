package org.chinagcd.libjson

import org.chinagcd.libjson.serializer.SerializerDispatcher

object JSON {
    private val dispatcher = SerializerDispatcher()

    @JvmStatic
    fun dateFormat(pattern: String): JSON {
        dispatcher.feature.dateFormat = pattern
        return this
    }

    @JvmStatic
    fun writeNull(flag: Boolean): JSON {
        dispatcher.feature.writeNull = flag
        return this
    }

    @JvmStatic
    fun pretty(flag: Boolean): JSON {
        dispatcher.feature.pretty = flag
        return this
    }

    @JvmStatic
    fun stringify(data: Any?): String {
        return dispatcher.dispatch(data)
    }
}