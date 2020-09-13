package com.c2v4.advent19

fun programAlarm(input: String, first: Int, second: Int): Int {
  val array = prepareComputerInput(input)
  array[1] = first
  array[2] = second
  return compute(array).registers[0]
}

fun main(args: Array<String>) {
  println(programAlarm("day2.txt".asResource(), 12, 2))
}
