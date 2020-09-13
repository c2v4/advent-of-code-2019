package com.c2v4.advent19

fun String.asResource() = Thread.currentThread().contextClassLoader.getResource(this).readText()

val splitRegex = Regex("\\r?\\n")

fun prepareComputerInput(input: String) = input.split(',').map { it.toInt() }.toIntArray()

fun String.asComputerinput() = prepareComputerInput(this)