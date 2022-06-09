package org.chinagcd.libjson.serializer

import org.chinagcd.libjson.annotation.JSONKey
import org.chinagcd.libjson.constants.DateFormats
import org.chinagcd.libjson.enums.SerializeFeature
import org.chinagcd.libjson.enums.SerializerType
import org.chinagcd.libjson.factory.SerializerFactory
import java.lang.reflect.Field
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * dispatch different type to different serializer
 */
open class SerializerDispatcher{

    var feature: SerializeFeature = SerializeFeature()

    fun dispatch(value: Any?): String {
        return dispatch(value, value, 0)
    }

    fun dispatch(field: Any?, value: Any?, indent: Int): String {
        return dispatch(field, value, indent, true)
    }

    fun dispatch(field: Any?, value: Any?, indent: Int, useIndent: Boolean): String {
        return value?.let {
            var str = ""
            when (value) {
                is Array<*> -> str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, value.toList(), indent, useIndent)
                is Collection<*> -> str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, value, indent, useIndent)

                is ByteArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is ShortArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is IntArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is CharArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is FloatArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is DoubleArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is LongArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is BooleanArray -> {
                    val list = ArrayList<Any?>()
                    for (i in 0 until value.size) {
                        list.add(value[i])
                    }
                    str += SerializerFactory.fetchSerializer(SerializerType.LIST, feature).serialize(field, list, indent, useIndent)
                }
                is Map<*, *> -> str += SerializerFactory.fetchSerializer(SerializerType.MAP, feature).serialize(field, value, indent, useIndent)
                is String, is Boolean, is Char, is Number,
                is LocalDate, is LocalDateTime, is Date, is LocalTime, is Calendar -> str = toJSONValue(value)
                else -> str += SerializerFactory.fetchSerializer(SerializerType.OBJECT, feature).serialize(field, value, indent, useIndent)
            }
            str
        } ?: "null"
    }

    fun shouldRecursive(item: Any?): Boolean {
        return item is Array<*> ||
                item is Collection<*> ||
                item is Map<*, *> ||
                isObject(item) == true ||
                item is IntArray ||
                item is CharArray ||
                item is ShortArray ||
                item is ByteArray ||
                item is LongArray ||
                item is FloatArray ||
                item is DoubleArray ||
                item is BooleanArray
    }

    /**
     * class has fields
     */
    fun isObject(value: Any?): Boolean {
        val isValue = value is String ||
                value is Number ||
                value is Boolean ||
                value is Char ||
                value is Date ||
                value is Timestamp ||
                value is LocalDate ||
                value is LocalDateTime ||
                value is LocalTime ||
                value is Calendar
       return if (isValue) false else value?.let {
            val clz = value::class.java
            val fields = clz.declaredFields
            val methods = clz.declaredFields.map { x -> x.name }
            fields.all { f ->
                f.trySetAccessible()
                val getMethod = "get" + f.name.substring(0, 1).uppercase().plus(f.name.substring(1))
                val setMethod = "set" + f.name.substring(0, 1).uppercase().plus(f.name.substring(1))
//                methods.contains(getMethod) && methods.contains(setMethod)
                true
            }
        } ?: false
    }

    fun toJSONKey(item: Any?): String = """"$item"""".trimIndent()

    fun toJSONValue(item: Any?): String =
        item?.let {
            when(item) {
                // only boolean and number returns its original value, others add "" boundres
                is Boolean, is Number -> item.toString()
                is Char -> """"$item""""
                is Date, is Timestamp -> """"${SimpleDateFormat(DateFormats.DATE_TIME).format(item)}"""".trimIndent()
                is LocalTime -> """"${DateTimeFormatter.ofPattern(DateFormats.TIME).format(item)}"""".trimIndent()
                is LocalDateTime -> """"${DateTimeFormatter.ofPattern(DateFormats.DATE_TIME).format(item)}"""".trimIndent()
                is LocalDate -> """"${DateTimeFormatter.ofPattern(DateFormats.DATE).format(item)}"""".trimIndent()
                else -> """"$item"""".trimIndent()
            }
        } ?: "null"


    fun calcIndent(indent: Int) = calcIndent(indent, false, feature.pretty)

    fun calcIndent(indent: Int, empty: Boolean, pretty: Boolean): String {
        if (empty || !pretty) {
            return ""
        }
        val sb = StringBuffer()
        for (i in 1..indent) {
            sb.append("  ")
        }
        return sb.toString()
    }

    fun shouldNewLine(item: Any?): Any? {
        if (feature.writeNull) {
            return if (feature.pretty) "\n" else ""
        }else {
            return item?.let {
                if (feature.pretty) "\n" else ""
            } ?: ""
        }
    }

    /**
     * parse json key name, if annotation JSONKey present, use specified name
     */
    fun computeName(field: Field): String {
        field.trySetAccessible()
        val anno = field.getAnnotation(JSONKey::class.java)
        return anno?.let { it.name } ?: field.name
    }
}