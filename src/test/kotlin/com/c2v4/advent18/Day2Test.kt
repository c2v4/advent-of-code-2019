package com.c2v4.advent18

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Day2Test{

    @Test
    fun day1test() {
        Assertions.assertThat(programAlarm("1,9,10,3,2,3,11,0,99,30,40,50",9,10)).isEqualTo(3500)
    }
}