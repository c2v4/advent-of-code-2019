package com.c2v4.advent18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test {

  @Test
  fun day1test() {
    Assertions.assertThat(calculateFuel("12")).isEqualTo(2)
  }
}
