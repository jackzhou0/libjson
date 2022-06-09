package org.chinagcd.libjson.annotation

@Retention
@Target(AnnotationTarget.FIELD)
annotation class JSONKey(
    /**
     * serialize a field or not, default is true
     */
    val serialize: Boolean = true,
    /**
     * write a json key to field
     */
    val deserialize: Boolean = true,

    /**
     * write field as specified name
     */
    val name: String = ""
)
