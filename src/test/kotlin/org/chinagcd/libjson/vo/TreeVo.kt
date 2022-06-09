package org.chinagcd.libjson.vo

data class TreeVo(
    var id: Int? = null,
    var name: String? = null,
    var children: MutableList<TreeVo>? = null
)