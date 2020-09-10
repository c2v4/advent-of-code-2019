package com.c2v4.advent18

import java.lang.IllegalStateException

fun programAlarm(input: String, first: Int, second: Int): Int {
  val array = input.split(',').map { it.toInt() }.toIntArray()
  array[1] = first
  array[2] = second
  var pointer = 0
  loop@ while (true) {
    val instruction = array[pointer]
    when (instruction) {
      1 -> {
        array[array[pointer + 3]] = array[array[pointer + 1]] + array[array[pointer + 2]]
      }
      2 -> {
        array[array[pointer + 3]] = array[array[pointer + 1]] * array[array[pointer + 2]]
      }
      99 -> {
        break@loop
      }
      else -> throw IllegalStateException()
    }
    pointer += 4
  }
  return array[0]
}
fun main(args: Array<String>) {
  println(programAlarm("day2.txt".asResource(), 12, 2))
}
