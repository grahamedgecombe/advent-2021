package com.grahamedgecombe.advent2021.day3

import com.grahamedgecombe.advent2021.Puzzle

object Day3 : Puzzle<List<String>>(3) {
    override fun parse(input: Sequence<String>): List<String> {
        return input.toList()
    }

    private fun getMostCommonValue(input: List<String>, n: Int): Int {
        val zeroes = input.count { number -> number[n] == '0' }
        val ones = input.size - zeroes
        return if (ones >= zeroes) 1 else 0
    }

    private fun invert(value: Int): Int {
        return value.inv() and 0x1
    }

    override fun solvePart1(input: List<String>): Int {
        require(input.isNotEmpty())

        val bits = input.first().length

        var gamma = 0
        var epsilon = 0

        for (n in 0 until bits) {
            val mostCommon = getMostCommonValue(input, n)
            val leastCommon = invert(mostCommon)

            gamma = (gamma shl 1) or mostCommon
            epsilon = (epsilon shl 1) or leastCommon
        }

        return gamma * epsilon
    }
}
