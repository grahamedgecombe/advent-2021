package com.grahamedgecombe.advent2021.day6

import com.grahamedgecombe.advent2021.Puzzle

object Day6 : Puzzle<List<Int>>(6) {
    override fun parse(input: Sequence<String>): List<Int> {
        return input.first()
            .splitToSequence(',')
            .map(String::toInt)
            .toList()
    }

    private fun solve(input: List<Int>, days: Int): Long {
        val fishByTimer = LongArray(9)

        for (timer in input) {
            fishByTimer[timer]++
        }

        for (day in 0 until days) {
            val newFish = fishByTimer[0]

            for (timer in 0..7) {
                fishByTimer[timer] = fishByTimer[timer + 1]
            }

            fishByTimer[6] += newFish
            fishByTimer[8] = newFish
        }

        return fishByTimer.sum()
    }

    override fun solvePart1(input: List<Int>): Long {
        return solve(input, 80)
    }

    override fun solvePart2(input: List<Int>): Long {
        return solve(input, 256)
    }
}
