package com.grahamedgecombe.advent2021.day14

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

object Day14 : Puzzle<Day14.Input>(14) {
    private val DELIMITER = Regex(" -> ")

    data class Input(
        val template: String,
        val rules: Map<Pair<Char, Char>, Char>
    ) {
        fun solve(steps: Int): Long {
            require(template.isNotEmpty())

            var pairFrequencies = mutableMapOf<Pair<Char, Char>, Long>()

            for (i in 0 until template.length - 1) {
                val pair = Pair(template[i], template[i + 1])
                increment(pairFrequencies, pair, 1)
            }

            // The last character never changes, so we add it in at the end.

            for (step in 0 until steps) {
                val next = mutableMapOf<Pair<Char, Char>, Long>()

                for ((pair, frequency) in pairFrequencies) {
                    val insertion = rules[pair] ?: throw UnsolvableException()

                    increment(next, Pair(pair.first, insertion), frequency)
                    increment(next, Pair(insertion, pair.second), frequency)
                }

                pairFrequencies = next
            }

            val charFrequencies = mutableMapOf<Char, Long>()
            for ((pair, frequency) in pairFrequencies) {
                increment(charFrequencies, pair.first, frequency)
            }
            increment(charFrequencies, template.last(), 1)

            val min = charFrequencies.values.minOrNull() ?: throw UnsolvableException()
            val max = charFrequencies.values.maxOrNull() ?: throw UnsolvableException()
            return max - min
        }

        private fun <T> increment(
            frequencies: MutableMap<T, Long>,
            key: T,
            increment: Long
        ) {
            val value = frequencies.getOrDefault(key, 0)
            frequencies[key] = value + increment
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

    override fun solvePart1(input: Input): Long {
        return input.solve(10)
    }

    override fun solvePart2(input: Input): Long {
        return input.solve(40)
    }
}
