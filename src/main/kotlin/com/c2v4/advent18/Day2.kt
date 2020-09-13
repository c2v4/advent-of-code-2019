package com.c2v4.advent18

fun programAlarm(input: String, first: Int, second: Int): Int {
  val array = input.split(',').map { it.toInt() }.toIntArray()
  array[1] = first
  array[2] = second
  return compute(array).registers[0]
}

fun main(args: Array<String>) {
  println(programAlarm("day2.txt".asResource(), 12, 2))
}
