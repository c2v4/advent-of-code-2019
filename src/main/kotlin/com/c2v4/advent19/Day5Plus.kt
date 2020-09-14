package com.c2v4.advent19


fun sunnyAsteroidsPlus(input:String) =
    compute(ComputerState(registers = input.split(',').map { it.toInt() }.toIntArray(), input = 5)).output.last()


fun main(args: Array<String>) {
  println(sunnyAsteroidsPlus("day5.txt".asResource()))
}
