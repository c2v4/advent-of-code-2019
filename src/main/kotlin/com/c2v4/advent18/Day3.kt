package com.c2v4.advent18

import java.lang.Integer.min
import java.util.*
import kotlin.math.abs
import kotlin.math.max

fun crossedWires(wireScheme: String): Int {
  val ZERO = Point(0, 0)
  val wires =
      wireScheme
          .split("\n")
          .map {
            it.split(",")
                .fold(
                    mutableListOf<Wire>() to ZERO,
                    { (acc, previousPoint), s ->
                      val (wire, nextPoint) = when (s.first()) {
                        'R' -> {
                          val endPoint = previousPoint.copy(x = previousPoint.x + s.drop(1).toInt())
                          Wire(previousPoint, endPoint) to endPoint
                        }
                        'L' -> {
                          val endPoint = previousPoint.copy(x = previousPoint.x - s.drop(1).toInt())
                          Wire(endPoint, previousPoint) to endPoint
                        }
                        'U' -> {
                          val endPoint = previousPoint.copy(y = previousPoint.y + s.drop(1).toInt())
                          Wire(previousPoint, endPoint) to endPoint
                        }
                        'D' -> {
                          val endPoint = previousPoint.copy(y = previousPoint.y - s.drop(1).toInt())
                          Wire(endPoint, previousPoint) to endPoint
                        }
                        else -> throw IllegalArgumentException()
                      }
                      acc.add(wire)
                      acc to nextPoint
                    })
                .first
          }
          .map { it.partition { wire -> wire.isHorizontal() } }
          .map { (horizontal, vertical) ->
            TreeMap(horizontal.map { wire -> wire.start.y to wire }.toMap()) to
                TreeMap(vertical.map { wire -> wire.start.x to wire }.toMap())
          }
  val (firstWires, secondWires) = wires[0] to wires[1]
  val intersections =
      (firstWires.first.values.flatMap { horizontal ->
        secondWires
            .second
            .subMap(
                min(horizontal.start.x, horizontal.finish.x),
                max(horizontal.start.x, horizontal.finish.x))
            .filter { (_, vertical) ->
              horizontal.start.y in (vertical.start.y..vertical.finish.y)
            }
            .map {
              Point(it.key, horizontal.start.y)
            }
      } +
          firstWires.second.values.flatMap { vertical ->
            secondWires.first.subMap(
                    min(vertical.start.y, vertical.finish.y),
                    max(vertical.start.y, vertical.finish.y))
                .filter { (_, horizontal) ->
                  vertical.start.x in (horizontal.start.x..horizontal.finish.x)
                }
                .map { Point(vertical.start.x, it.key) }
          }).toSet().minus(ZERO)
  return intersections.map { it.manhattanDistance(ZERO) }.min() ?: 0
}

data class Point(val x: Int, val y: Int)

data class Wire(val start: Point, val finish: Point)

fun Wire.isHorizontal() = this.start.y == this.finish.y

fun Point.manhattanDistance(other: Point): Int = abs(this.x - other.x) + abs(this.y - other.y)

fun main() {
  println(crossedWires("day3.txt".asResource()))
}
