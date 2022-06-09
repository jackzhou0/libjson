package org.chinagcd.libjson.dispatcher

/**
 * base serialize for array, map, object
 */
interface Serializer {
    /**
     *  abstract method for different serialize object, include array, map, object
     *  @param field current field of the object
     *  @param value current value of the object
     *  @param indent current indent for this field
     *  @param useIndent if use indent, for example
     *  <code>
     *      {
     *          "arr": [
     *
     *          ]
     *      }
     *   </code>
     */
    fun serialize(field: Any?, value: Any?, indent: Int, useIndent: Boolean): String
}