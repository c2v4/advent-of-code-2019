package com.c2v4.advent18

fun calculateFuel(input:String) = input.split(splitRegex).map { it.toInt() }.map{ it/3 -2 }.sum()

    fun main(args: Array<String>) {
        println(calculateFuel("day1.txt".asResource()))
    }
