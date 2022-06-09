package org.chinagcd.libjson.serializer

import org.chinagcd.libjson.dispatcher.Serializer
import org.chinagcd.libjson.enums.SerializeFeature

class MapSerializer(feature: SerializeFeature) : Serializer, SerializerDispatcher(feature) {

    override fun serialize(field: Any?, value: Any?, indent: Int, useIndent: Boolean): String {
        var str = ""
        str = (if (useIndent) calcIndent(indent) else "").plus("{")
        val map = value?.let { it as Map<*, *> } ?: HashMap<String, Any?>()
        // 筛选出来所有需要处理的key
        val keys = map.keys.toList()
            .filter { x ->
                val fieldValue = map[x]
                if (!feature.writeNull)
                    fieldValue?.let { true } ?: false
                else true
            }

        keys.forEachIndexed { index, key ->
            val item = map[key]
            str += (if (index == keys.size - 1 && str.trim() != "{" && !str.trim().endsWith(",")) "," else "").plus(
                shouldNewLine(item)
            )
            if (shouldRecursive(item)) {
                str += calcIndent(indent + 1) + toJSONKey(key).plus(keySplitor(feature.pretty))
                    .plus(dispatch(key, item, indent + 1, false))
            } else {
                str += calcIndent(indent + 1) + toJSONKey(key) + keySplitor(feature.pretty) + toJSONValue(item)
            }
            str += if (index != keys.size - 1) "," else ""
        }
        str += (if (keys.isNotEmpty()) (if (feature.pretty) "\n" else "") else "") + calcIndent(
            indent,
            keys.isEmpty(),
            feature.pretty
        ).plus("}")
        return str
    }
}