package com.c2v4.advent19

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeExactly

class Day5PlusTest :
    StringSpec({
      "!The above example program uses an input instruction to ask for a single numb" +
          "er. The program will then output 999 if the input value is below 8, output 1000 " +
          "if the input value is equal to 8, or output 1001 if the input value is greater than 8." {
            compute(
                    ComputerState(
                        ("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")
                            .asComputerinput(),
                        input = 7))
                .output
                .last() shouldBeExactly 999
            compute(
                    ComputerState(
                        ("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")
                            .asComputerinput(),
                        input = 8))
                .output
                .last() shouldBeExactly 1000
            compute(
                    ComputerState(
                        ("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                                "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104," +
                                "999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99")
                            .asComputerinput(),
                        input = 9))
                .output
                .last() shouldBeExactly 1001
          }
    })
