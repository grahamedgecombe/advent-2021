package com.grahamedgecombe.advent2021.day14

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

object Day14 : Puzzle<Day14.Input>(14) {
    private val DELIMITER = Regex(" -> ")

    data class Input(
        val template: String,
        val rules: Map<Pair<Char, Char>, Char>
    ) {
        fun solve(): Int {
            val frequencies = mutableMapOf<Char, Int>()

            for (i in 0 until template.length - 1) {
                val pair = Pair(template[i], template[i + 1])
                val last = i == template.length - 2
                countFrequencies(frequencies, 10, pair, last)
            }

            val min = frequencies.values.minOrNull() ?: throw UnsolvableException()
            val max = frequencies.values.maxOrNull() ?: throw UnsolvableException()
            return max - min
        }

        private fun increment(
            frequencies: MutableMap<Char, Int>,
            key: Char
        ) {
            val value = frequencies.getOrDefault(key, 0)
            frequencies[key] = value + 1
        }

        private fun countFrequencies(
            frequencies: MutableMap<Char, Int>,
            steps: Int,
            pair: Pair<Char, Char>,
            last: Boolean
        ) {
            if (steps == 0) {
                increment(frequencies, pair.first)

                if (last) {
                    increment(frequencies, pair.second)
                }

                return
            }

            val inserted = rules[pair] ?: throw UnsolvableException()
            countFrequencies(frequencies, steps - 1, Pair(pair.first, inserted), false)
            countFrequencies(frequencies, steps - 1, Pair(inserted, pair.second), last)
        }
    }

    override fun parse(input: Sequence<String>): Input {
        var parsingTemplate = true
        var template: String? = null
        val rules = mutableMapOf<Pair<Char, Char>, Char>()

        for (s in input) {
            if (s.isEmpty()) {
                parsingTemplate = false
            } else if (parsingTemplate) {
                template = s
            } else {
                val (a, b) = s.split(DELIMITER, limit = 2)
                require(a.length == 2 && b.length == 1)

                val pair = Pair(a[0], a[1])
                rules[pair] = b[0]
            }
        }

        template ?: throw IllegalArgumentException()
        return Input(template, rules)
    }

    override fun solvePart1(input: Input): Int {
        return input.solve()
    }
}
