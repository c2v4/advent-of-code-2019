package com.c2v4.advent19

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class Day3PlusTest : AnnotationSpec() {

  @Test
  fun day3PlusTest0() {
    assertThat(crossedWiresPlus("R8,U5,L5,D3\n" +
            "U7,R6,D4,L4")).isEqualTo(30)
  }
  @Test
  fun day3PlusTest1() {
    assertThat(crossedWiresPlus("R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
            "U62,R66,U55,R34,D71,R55,D58,R83")).isEqualTo(610)
  }

  @Test
  fun day3PlusTest2() {
    assertThat(crossedWiresPlus("R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
            "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7")).isEqualTo(410)
  }


}
