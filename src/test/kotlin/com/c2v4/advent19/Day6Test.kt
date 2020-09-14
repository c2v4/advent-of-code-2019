package com.c2v4.advent19

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day6Test :
    StringSpec({
      "The total number of direct and indirect orbits in this example is 42." {
        universalOrbitMap(
            "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L") shouldBeExactly 42
      }
    })
