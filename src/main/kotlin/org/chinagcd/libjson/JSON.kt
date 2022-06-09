package org.chinagcd.libjson

import org.chinagcd.libjson.serializer.SerializerDispatcher

class JSON {
    val dispatcher = SerializerDispatcher()

    fun dateFormat(pattern: String): JSON {
        dispatcher.feature.dateFormat = pattern
        return this
    }

    fun writeNull(flag: Boolean): JSON {
        dispatcher.feature.writeNull = flag
        return this
    }

    fun pretty(flag: Boolean = true): JSON {
        dispatcher.feature.pretty = flag
        return this
    }

    fun stringify(data: Any?): String {
        return dispatcher.dispatch(data)
    }

}