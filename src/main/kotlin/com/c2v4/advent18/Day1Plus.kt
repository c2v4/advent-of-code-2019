package com.c2v4.advent18

fun calculateFuelIncludingFuel(input: String): Int = input.split(splitRegex)
        .map { it.toInt() }
        .map { it / 3 - 2 }
        .map { countdownFuel(it) }
        .sum()
fun countdownFuel(startingFuel: Int): Int {
  var fuel = startingFuel
  var sum = startingFuel
  while (fuel > 1) {
    val toAdd = fuel / 3 - 2
    if (toAdd < 1) break
    sum += toAdd
    fuel = toAdd
  }
  return sum
}
fun main(args: Array<String>) {
  println(calculateFuelIncludingFuel("day1.txt".asResource()))
}
