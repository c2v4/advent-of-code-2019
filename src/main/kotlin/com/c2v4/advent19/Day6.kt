package com.c2v4.advent19

import com.google.common.graph.Graph
import com.google.common.graph.GraphBuilder
import com.google.common.graph.MutableGraph

const val CENTER_OF_MASS = "COM"
fun universalOrbitMap(input: String):Int {
  return createGraph(input)
      .let { calculateOrbits(it,CENTER_OF_MASS,0) }
}

fun createGraph(input: String): Graph<String> {
  return input
      .split(splitRegex)
      .map { it.split(')') }
      .fold(
          GraphBuilder.directed().build<String>(), { acc, list ->
        acc.putEdge(list[0], list[1])
        acc
      })
}

private fun calculateOrbits(graph: Graph<String>, s: String, level: Int): Int{
  val successors = graph.successors(s)
  return level + successors.asSequence().map { calculateOrbits(graph,it,level+1) }.sum()
}

fun main(args: Array<String>) {
  println(universalOrbitMap("day6.txt".asResource()))
}
