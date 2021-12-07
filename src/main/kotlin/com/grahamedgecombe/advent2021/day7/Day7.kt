package com.grahamedgecombe.advent2021.day7

import com.grahamedgecombe.advent2021.Puzzle
import kotlin.math.abs
import kotlin.math.min

object Day7 : Puzzle<List<Int>>(7) {
    override fun parse(input: Sequence<String>): List<Int> {
        return input.first()
            .splitToSequence(',')
            .map(String::toInt)
            .toList()
    }

    override fun solvePart1(input: List<Int>): Int {
        var best = Int.MAX_VALUE

        val min = input.minOrNull() ?: throw IllegalArgumentException()
        val max = input.maxOrNull() ?: throw IllegalArgumentException()

        for (target in min..max) {
            var fuel = 0

            for (start in input) {
                fuel += abs(target - start)
            }

            best = min(best, fuel)
        }

        return best
    }
}
