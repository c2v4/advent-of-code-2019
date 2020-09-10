package com.c2v4.advent18

fun findAlarm(input: String, toAchieve: Int): Int {
  (0..100).forEach { x ->
    (0..100).forEach { y ->
      val result = programAlarm(input, x, y)
      println("x = ${x}")
      println("y = ${y}")
      println("result = ${result}")
      if (result == toAchieve) return 100 * x + y
    }
  }
  return 0
}

fun main(args: Array<String>) {
  println(findAlarm("day2.txt".asResource(), 19690720))
}
