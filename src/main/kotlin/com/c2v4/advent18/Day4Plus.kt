package com.c2v4.advent18


fun main(args: Array<String>) {
  println(secureContainerPlus(367479..893698))
}

fun secureContainerPlus(intRange: IntRange): Int =
 intRange.asSequence().map { it.toString() }.filter { containsOnlyDouble(it) }.filter { notDecreasing(it) }.count()

fun containsOnlyDouble(string: String): Boolean = ("a"+string+"a").windowed(4).any {
  (it[1]==it[2] && it[0]!=it[1] && it[2]!= it[3])
}


