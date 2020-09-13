package com.c2v4.advent19

import io.kotest.core.spec.style.AnnotationSpec
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day1Test : AnnotationSpec() {

  @Test
  fun day1test() {
    Assertions.assertThat(calculateFuel("12")).isEqualTo(2)
  }
}
