package com.c2v4.advent18

import io.reactivex.rxjava3.kotlin.toObservable
import kotlin.math.abs
import kotlin.math.min

val ZERO = Point(0, 0)

fun crossedWires(wireScheme: String): Int {
  //  val wires =
  //      createWires(wireScheme)
  //          .map { it.toList().blockingGet().partition { wire -> wire.isHorizontal() } }
  //          .map { (horizontal, vertical) ->
  //            TreeMap(horizontal.map { wire -> wire.start.y to wire }.toMap()) to
  //                TreeMap(vertical.map { wire -> wire.start.x to wire }.toMap())
  //          }
  //  val (firstWires, secondWires) = wires[0] to wires[1]
  //  val intersections =
  //      (firstWires.first.values.flatMap { horizontal ->
  //            secondWires
  //                .second
  //                .subMap(
  //                    min(horizontal.start.x, horizontal.finish.x),
  //                    max(horizontal.start.x, horizontal.finish.x))
  //                .filter { (_, vertical) ->
  //                  horizontal.start.y in
  //                      (min(vertical.start.y, vertical.finish.y)..max(
  //                              vertical.start.y, vertical.finish.y))
  //                }
  //                .map { Point(it.key, horizontal.start.y) }
  //          } +
  //              firstWires.second.values.flatMap { vertical ->
  //                secondWires
  //                    .first
  //                    .subMap(
  //                        min(vertical.start.y, vertical.finish.y),
  //                        max(vertical.start.y, vertical.finish.y))
  //                    .filter { (_, horizontal) ->
  //                      vertical.start.x in
  //                          (min(horizontal.start.x, horizontal.finish.x)..max(
  //                                  horizontal.start.x, horizontal.finish.x))
  //                    }
  //                    .map { Point(vertical.start.x, it.key) }
  //              })
  //          .toSet()
  //          .minus(ZERO)
  //  return intersections.map { it.manhattanDistance(ZERO) }.min() ?: 0
  val blockingGet = createIntersections(wireScheme).toList().blockingGet()
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

fun Wire.length() = if (isHorizontal()) abs(start.y - finish.y) else abs(start.x - finish.x)

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
