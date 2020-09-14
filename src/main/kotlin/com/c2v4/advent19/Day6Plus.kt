package com.c2v4.advent19

fun universalOrbitMapPlus(input: String):Int {
  createGraph(input).let{ graph ->
    val checkedNodes = mutableSetOf<String>()
    var toCheck = setOf("YOU")
    var transfers = -1
    while(toCheck.isNotEmpty()){
      if(toCheck.contains("SAN")) return transfers -1
      checkedNodes.addAll(toCheck)
      toCheck = toCheck.flatMap { graph.adjacentNodes(it) }.filter { !checkedNodes.contains(it) }.toSet()
      transfers+=1
    }
    return -1
  }
}

fun main(args: Array<String>) {
  println(universalOrbitMapPlus("day6.txt".asResource()))
}
