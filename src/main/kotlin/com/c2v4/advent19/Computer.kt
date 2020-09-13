package com.c2v4.advent19

import arrow.core.Either
import arrow.core.flatMap
import arrow.syntax.function.partially2
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.math.max
import kotlin.math.pow

fun compute(array: IntArray): ComputerState {
  return compute(Either.right(ComputerState(array)))
}

fun compute(computerState: ComputerState): ComputerState {
  return compute(Either.right(computerState))
}

private fun compute(state: Either<ComputerState, ComputerState>): ComputerState {
  val newState: Either<ComputerState, ComputerState> =
      state.flatMap { computerState -> getInstructionLogic(computerState).invoke(computerState) }
  return newState.fold({ it }, { compute(Either.right(it)) })
}

private fun getInstructionLogic(
    computerState: ComputerState
): (ComputerState) -> Either<ComputerState, ComputerState> {
  val instructionWord = computerState.registers[computerState.pointer]
  val opCode = instructionWord % 100
  return instructionMap
      .getOrElse(opCode) { throw IllegalStateException() }
      .partially2(getParameterExtractors(instructionWord))
}

fun getParameterExtractors(instructionWord: Int): Array<(IntArray, Int) -> Int> =
    Array(max(getNumberOfParameters(instructionWord % 100), 0)) { i ->
      val parameterMode = (instructionWord / (100 * 10.toDouble().pow(i)).toInt()) % 10
      val extractingFunction =
          parameterExtractorMap
              .getOrElse(parameterMode) { throw IllegalArgumentException() }
              .extractingFunction
      extractingFunction
    }

val parameterExtractorMap =
    mapOf(0 to ParameterExtractor.POSITION, 1 to ParameterExtractor.IMMEDIATE)

enum class ParameterExtractor(val extractingFunction: (IntArray, Int) -> Int) {
  POSITION({ array, pointer -> array.getOrElse(array.getOrElse(pointer) { -1 }) { -1 } }),
  IMMEDIATE({ array, pointer -> array.getOrElse(pointer) { -1 } })
}

private fun getNumberOfParameters(opCode: Int): Int = numberOfParameterMap.getOrElse(opCode-1,{0})

private val numberOfParameterMap = arrayOf(3, 3, 1, 1, 2, 2, 3, 3)

private val instructionMap:
    Map<
        Int,
        (ComputerState, Array<(IntArray, Int) -> Int>) -> Either<ComputerState, ComputerState>> =
    mapOf(
            1 to
                { state: ComputerState, parameters: IntArray ->
                  val newRegisters = state.registers.copyOf()
                  newRegisters[newRegisters[state.pointer + 3]] = parameters[0] + parameters[1]
                  state.copy(
                      registers = newRegisters, pointer = state.pointer + parameters.size + 1)
                },
            2 to
                { state: ComputerState, parameters: IntArray ->
                  val newRegisters = state.registers.copyOf()
                  newRegisters[newRegisters[state.pointer + 3]] = parameters[0] * parameters[1]
                  state.copy(
                      registers = newRegisters, pointer = state.pointer + parameters.size + 1)
                },
            3 to
                { state: ComputerState, parameters: IntArray ->
                  val newRegisters = state.registers.copyOf()
                  newRegisters[newRegisters[state.pointer + 1]] = state.input
                  state.copy(
                      registers = newRegisters, pointer = state.pointer + parameters.size + 1)
                },
            4 to
                { state: ComputerState, parameters: IntArray ->
                  state.copy(
                      output = state.output.plus(parameters[0]),
                      pointer = state.pointer + parameters.size + 1)
                },
            5 to
                { state: ComputerState, parameters: IntArray ->
                  state.copy(
                      output = state.output.plus(state.registers[parameters[0]]),
                      pointer = state.pointer + parameters.size + 1)
                },
            6 to
                { state: ComputerState, parameters: IntArray ->
                  state.copy(
                      output = state.output.plus(state.registers[parameters[0]]),
                      pointer = state.pointer + parameters.size + 1)
                },
            7 to
                { state: ComputerState, parameters: IntArray ->
                  state.copy(
                      output = state.output.plus(state.registers[parameters[0]]),
                      pointer = state.pointer + parameters.size + 1)
                },
            8 to
                { state: ComputerState, parameters: IntArray ->
                  state.copy(
                      output = state.output.plus(state.registers[parameters[0]]),
                      pointer = state.pointer + parameters.size + 1)
                })
        .mapValues<
            Int,
            (ComputerState, IntArray) -> ComputerState,
            (ComputerState, Array<(IntArray, Int) -> Int>) -> Either<
                    ComputerState, ComputerState>> { (_, transformation) ->
          { state: ComputerState, parameterExtractors: Array<(IntArray, Int) -> Int> ->
            val parameters =
                parameterExtractors
                    .mapIndexed { index, function ->
                      function.invoke(state.registers, state.pointer + 1 + index)
                    }
                    .toIntArray()
            Either.right(transformation(state, parameters))
          }
        }
        .plus(99 to { computerState, _ -> Either.left(computerState) })

data class ComputerState(
    val registers: IntArray,
    val pointer: Int = 0,
    val input: Int = 0,
    val output: IntArray = intArrayOf())
