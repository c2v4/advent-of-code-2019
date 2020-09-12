package com.c2v4.advent18

import io.reactivex.rxjava3.kotlin.toObservable
import kotlin.math.abs
import kotlin.math.min

val ZERO = Point(0, 0)

fun crossedWires(wireScheme: String): Int {
  return createIntersections(wireScheme)
      .flatMap { state -> state.intersections.flatMap { it }.toObservable() }
      .map { it.first.manhattanDistance(ZERO) }
      .filter { t -> t != 0 }
      .reduce(Integer.MAX_VALUE){ t1, t2 -> min(t1,t2) }
      .blockingGet()
}

fun createWires(wireScheme: String) =
    wireScheme.split("\n").mapIndexed { index, line ->
      line
          .split(",")
          .toObservable()
          .scan(Wire(ZERO, ZERO) to ZERO) { (_, previousPoint), s ->
            val (wire, nextPoint) = when (s.first()) {
              'R' -> {
                val endPoint = previousPoint.copy(x = previousPoint.x + s.drop(1).toInt())
                Wire(previousPoint, endPoint, index) to endPoint
              }
              'L' -> {
                val endPoint = previousPoint.copy(x = previousPoint.x - s.drop(1).toInt())
                Wire(previousPoint, endPoint, index) to endPoint
              }
              'U' -> {
                val endPoint = previousPoint.copy(y = previousPoint.y + s.drop(1).toInt())
                Wire(previousPoint, endPoint, index) to endPoint
              }
              'D' -> {
                val endPoint = previousPoint.copy(y = previousPoint.y - s.drop(1).toInt())
                Wire(previousPoint, endPoint, index) to endPoint
              }
              else -> throw IllegalArgumentException()
            }
            wire to nextPoint
          }
          .skip(1)
          .map { it.first }
    }

data class Point(val x: Int, val y: Int)

data class Wire(val start: Point, val finish: Point, val source: Int = 0)

fun Wire.length() = if (isHorizontal()) abs(start.x - finish.x) else abs(start.y - finish.y)

fun Wire.getAlignment() = if (isHorizontal()) Alignment.HORIZONTAL else Alignment.VERTICAL

enum class Alignment {
  HORIZONTAL,
  VERTICAL
}

fun Wire.isHorizontal() = this.start.y == this.finish.y

fun Point.manhattanDistance(other: Point): Int = abs(this.x - other.x) + abs(this.y - other.y)

fun main() {
  println(crossedWires("day3.txt".asResource()))
}
