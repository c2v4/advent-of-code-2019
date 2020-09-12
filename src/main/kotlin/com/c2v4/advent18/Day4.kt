package com.c2v4.advent18


fun main(args: Array<String>) {
  println(secureContainer(367479..893698))
}

fun secureContainer(intRange: IntRange): Int =
 intRange.asSequence().map { it.toString() }.filter { containsDouble(it) }.filter { notDecreasing(it) }.count()

fun notDecreasing(string: String): Boolean =
  string.windowed(2).none {it[0]>it[1]}



fun containsDouble(string: String): Boolean =
    string.windowed(2).any { it[0]==it[1] }

