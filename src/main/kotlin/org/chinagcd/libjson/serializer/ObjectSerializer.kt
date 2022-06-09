package org.chinagcd.libjson.serializer

import org.chinagcd.libjson.annotation.JSONKey
import org.chinagcd.libjson.dispatcher.Serializer
import org.chinagcd.libjson.enums.SerializeFeature

class ObjectSerializer(feature: SerializeFeature): Serializer, SerializerDispatcher(feature) {

    override fun serialize(field: Any?, value: Any?, indent: Int, useIndent: Boolean): String {
        var str = ""
        return value?.let {
            str = (if (useIndent) calcIndent(indent) else "").plus("{")
            val fields = value::class.java.declaredFields
                .filter { x ->
                    x.trySetAccessible()
                    val fieldValue = x.get(value)
                    if (!feature.writeNull)
                        fieldValue?.let { true } ?: false
                    else true
                }
                .filter { x ->
                    x.trySetAccessible()
                    val anno = x.getAnnotation(JSONKey::class.java)
                    anno?.serialize ?: true
                }
                fields.forEachIndexed { index, field ->
                field.trySetAccessible()
                val item = field.get(value)
                str += (if (index == fields.size - 1 && str.trim() != "{" && !str.trim().endsWith(",")) "," else "").plus(shouldNewLine(item) )
                if (shouldRecursive(item)) {
                    str += calcIndent(indent + 1) + toJSONKey(field.name).plus(keySplitor(feature.pretty))
                        .plus(dispatch(field.name, item, indent + 1, false))
                } else {

                    str += calcIndent(indent + 1) + toJSONKey(computeName(field)) + keySplitor(feature.pretty) + toJSONValue(item)
                }
                str += if (index != fields.size - 1) "," else ""
            }
            str += (if (fields.isNotEmpty()) (if (feature.pretty) "\n" else "") else "") + calcIndent(indent, fields.isEmpty(), feature.pretty).plus("}")
            str
        } ?: run {
            System.err.println("kong duixiang")
            "{}"
        }
    }
}