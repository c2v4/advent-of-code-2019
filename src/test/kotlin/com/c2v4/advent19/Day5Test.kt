package com.c2v4.advent19

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day5Test:StringSpec({

  "Sunny Asteroids should be the same as in the website" {
    sunnyAsteroids("day5.txt".asResource()) shouldBeExactly 5821753
  }
})
