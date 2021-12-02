package com.grahamedgecombe.advent2021

import com.grahamedgecombe.advent2021.day1.Day1
import com.grahamedgecombe.advent2021.day2.Day2
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

@ExperimentalTime
fun main() {
    val puzzles = listOf<Puzzle<*>>(
        Day1,
        Day2,
    )

    for (puzzle in puzzles) {
        solve(puzzle)
    }
}

@ExperimentalTime
private fun <T> solve(puzzle: Puzzle<T>) {
    val input = puzzle.parse()

    val solutionPart1 = measureTimedValue {
        puzzle.solvePart1(input)
    }
    println("Day ${puzzle.number} Part 1: ${solutionPart1.value} (${solutionPart1.duration})")

    val solutionPart2 = measureTimedValue {
        puzzle.solvePart2(input)
    }
    if (solutionPart2.value != null) {
        println("Day ${puzzle.number} Part 2: ${solutionPart2.value} (${solutionPart2.duration})")
    }
}
