package com.c2v4.advent19

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day1PlusTest : AnnotationSpec() {

  @Test
  internal fun simpleTest() {
    Assertions.assertThat(calculateFuelIncludingFuel("14")).isEqualTo(2)
  }

  @Test
  internal fun firstTest() {
    Assertions.assertThat(calculateFuelIncludingFuel("1969")).isEqualTo(966)
  }

  @Test
  internal fun secondTest() {
    Assertions.assertThat(calculateFuelIncludingFuel("100756")).isEqualTo(50346)
  }
}
