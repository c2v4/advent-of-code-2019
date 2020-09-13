package com.c2v4.advent19

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.toObservable
import java.util.*
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun crossedWiresPlus(input: String): Int {
  val value =
      createIntersections(input)
          .flatMap { it.intersections.flatten().map { it.second }.toObservable() }
          .filter { it != 0 }
          .toList()
          .blockingGet()
          .min()
  return value!!
}

fun createIntersections(input: String) =
    createWires(input)
        .fold(Observable.empty<Wire>(), { acc, observable -> acc.mergeWith(observable) })
        .scan((Wire(ZERO, ZERO) to 0) to Array(2) { 0 }) { t1: Pair<Pair<Wire, Int>, Array<Int>>,
                                                           t2: Wire ->
          val soFar = t1.second[(t2.source)]
          t1.second[t2.source] += t2.length()
          (t2 to soFar) to t1.second
        }
        .skip(1)
        .map { it.first }
        .sorted(compareBy { t -> t.second })
        .map { it.first }
        .scan(WireState()) { state, wire ->
          state.addToKnownWires(wire)
          val newIntersections =
              listOf<MutableList<Pair<Point, Int>>>(mutableListOf(), mutableListOf())
          newIntersections[wire.source].addAll(state.calculateCollisions(wire))
          val newState = state.copy(intersections = newIntersections)
          newState.addDistance(wire)
          newState
        }
        .skip(1)

data class WireState(
    val wires: Array<EnumMap<Alignment, TreeMap<Int, Wire>>> =
        Array(2) {
          EnumMap(
              mapOf<Alignment, TreeMap<Int, Wire>>(
                  Alignment.HORIZONTAL to TreeMap(), Alignment.VERTICAL to TreeMap()))
        },
    val lengths: IntArray = IntArray(2),
    val lengthMap: MutableMap<Wire, Int> = mutableMapOf(),
    val intersections: List<List<Pair<Point, Int>>> = emptyList()
) {

  fun calculateCollisions(wire: Wire): Set<Pair<Point, Int>> =
      getWiresInRange(wire)
          .asSequence()
          .filter { (_, otherWire) -> checkColide(wire, otherWire) }
          .map {
            val intersection = createIntersection(wire, it.value)
            intersection to calculateDistance(wire, it.value, intersection)
          }
          .toSet()

  private fun calculateDistance(wire: Wire, other: Wire, intersection: Point): Int {
    return lengthMap.get(wire)!! + lengthMap.get(other)!! +
        abs(intersection.x - wire.start.x) +
        abs(intersection.y - wire.start.y) +
        abs(intersection.x - other.start.x) +
        abs(intersection.y - other.start.y)
  }

  private fun createIntersection(wire: Wire, other: Wire) =
      if (wire.isHorizontal()) Point(other.getFixedValue(), wire.getFixedValue())
      else Point(wire.getFixedValue(), other.getFixedValue())

  private fun checkColide(wire: Wire, other: Wire): Boolean =
      wire.getFixedValue() in other.getVariableRange()

  private fun getWiresInRange(wire: Wire) =
      wires[getOppositeSource(wire)][wire.getAlignment().opposite()].subMap(wire.getVariableRange())

  private fun getOppositeSource(wire: Wire): Int = if (wire.source == 0) 1 else 0

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as WireState

    if (!wires.contentEquals(other.wires)) return false

    return true
  }

  override fun hashCode(): Int {
    val result = wires.contentHashCode()
    return result
  }

  fun addDistance(wire: Wire) {
    lengths[wire.source] += wire.length()
  }
}

private fun Wire.getFixedValue(): Int = if (isHorizontal()) start.y else start.x

private fun <K : Comparable<K>, V> TreeMap<K, V>?.subMap(range: ClosedRange<K>) =
    this!!.subMap(range.start, range.endInclusive)

private fun Wire.getVariableRange(): IntRange =
    if (isHorizontal()) min(this.start.x, this.finish.x)..max(this.start.x, this.finish.x)
    else min(this.start.y, this.finish.y)..max(this.start.y, this.finish.y)

private fun Alignment.opposite(): Alignment =
    if (this == Alignment.VERTICAL) Alignment.HORIZONTAL else Alignment.VERTICAL

fun WireState.addToKnownWires(wire: Wire) {
  wires[wire.source][wire.getAlignment()]?.put(
      if (wire.isHorizontal()) wire.start.y else wire.start.x, wire)
  lengthMap[wire] = lengths[wire.source]
}

fun main() {
  println(crossedWiresPlus("day3.txt".asResource()))
}
