package com.c2v4.advent18

import java.lang.IllegalStateException

fun programAlarm(input: String, replace: Boolean = true): Int {
    val array = input.split(',').map { it.toInt() }.toIntArray()
    if (replace) {
        array[1] = 12
        array[2] = 2
    }
    var pointer = 0
    loop@ while(true){
        val instruction = array[pointer]
        when(instruction){
            1 -> {
                array[array[pointer+3]]=array[array[pointer+1]]+array[array[pointer+2]]
            }
            2 ->{
                array[array[pointer+3]]=array[array[pointer+1]]*array[array[pointer+2]]
            }
            99 ->{
                break@loop
            }
            else -> throw IllegalStateException()
        }
        pointer+=4
    }
    return array[0]
}


fun main(args: Array<String>) {
    println(programAlarm("day2.txt".asResource()))
}
