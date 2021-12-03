package com.grahamedgecombe.advent2021.day3

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

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

    override fun solvePart2(input: List<String>): Int {
        require(input.isNotEmpty())

        val bits = input.first().length

        val oxygenCandidates = input.toMutableList()
        val co2Candidates = input.toMutableList()

        for (n in 0 until bits) {
            val mostCommon = getMostCommonValue(oxygenCandidates, n).digitToChar()
            if (oxygenCandidates.size > 1) {
                oxygenCandidates.removeIf { number -> number[n] != mostCommon }
            }

            val leastCommon = invert(getMostCommonValue(co2Candidates, n)).digitToChar()
            if (co2Candidates.size > 1) {
                co2Candidates.removeIf { number -> number[n] != leastCommon }
            }
        }

        val oxygen = oxygenCandidates.singleOrNull() ?: throw UnsolvableException()
        val co2 = co2Candidates.singleOrNull() ?: throw UnsolvableException()

        return oxygen.toInt(2) * co2.toInt(2)
    }
}
