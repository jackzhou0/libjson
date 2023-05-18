package org.chinagcd.libjson

import org.chinagcd.libjson.vo.Node

fun main() {
    val node = Node("aa");
   node.and("bb");
    node.and("cc");
    node.and(Node("dd"))
    println(JSON().pretty(true).stringify(node))

}