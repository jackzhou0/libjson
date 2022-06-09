package org.chinagcd.libjson.vo

import org.chinagcd.libjson.annotation.JSONKey

public class AnnotationVo {
    var id: Int? = null
    var name: String? = null

    @JSONKey(name = "home_address")
    var addr: Boolean? = null

    @JSONKey(serialize = false)
    var sex: Int? = null
}