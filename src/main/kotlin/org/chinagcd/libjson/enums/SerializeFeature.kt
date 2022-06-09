package org.chinagcd.libjson.enums

data class SerializeFeature(
    /**
     * write json as pretty format
     * default is false
     */
    var pretty: Boolean = false,
    /**
     * serialize null fields or not, only valid for null key in map and null field in object
     * default is false
     */
    var writeNull: Boolean = false,
    /**
     * serialize date field as specified format, default is yyyy-MM-dd HH:mm:ss
     */
    var dateFormat: String = "yyyy-MM-dd HH:mm:ss"
)