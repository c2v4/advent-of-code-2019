package com.c2v4.advent19


fun sunnyAsteroids(input:String) =
    compute(ComputerState(registers = input.split(',').map { it.toInt() }.toIntArray(), input = 1)).output.last()


fun main(args: Array<String>) {
  println(sunnyAsteroids("day5.txt".asResource()))
}
