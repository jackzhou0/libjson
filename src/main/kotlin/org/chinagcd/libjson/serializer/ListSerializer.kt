package org.chinagcd.libjson.serializer

import org.chinagcd.libjson.dispatcher.Serializer
import org.chinagcd.libjson.enums.SerializeFeature

/**
 * serialize a list or array to json string
 * this class implements the serialize method signature in serializer class
 * and reuse some method in serializer dispatcher
 */
class ListSerializer(feature: SerializeFeature) : Serializer, SerializerDispatcher(feature) {

    override fun serialize(field: Any?, value: Any?, indent: Int, useIndent: Boolean): String {
        var str = ""
        str += (if (useIndent) calcIndent(indent) else "").plus("[")
        value?.let {
            val collection = it as Collection<*>
            collection.forEachIndexed { index, item ->
                str += (if (index == it.size - 1 && !str.trim().startsWith("[")) "," else "").plus(if (feature.pretty) "\n" else "")
                if (shouldRecursive(item)) {
                    str += dispatch(item, item, indent + 1)
                } else {
                    str += calcIndent(indent + 1) + toJSONValue(item)
                }
                str += if (index != it.size - 1) "," else ""
            }
            str += (if (it.isNotEmpty()) (if (feature.pretty) "\n" else "") else "") + calcIndent(indent, it.isEmpty(), feature.pretty).plus("]")
        }
        return str
    }
}