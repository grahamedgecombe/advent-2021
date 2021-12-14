package com.grahamedgecombe.advent2021.day14

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

object Day14 : Puzzle<Day14.Input>(14) {
    private val DELIMITER = Regex(" -> ")

    data class Input(
        val template: String,
        val rules: Map<Pair<Char, Char>, Char>
    ) {
        private data class Key(val pair: Pair<Char, Char>, val last: Boolean)

        fun solve(steps: Int): Long {
            var pairFrequencies = mutableMapOf<Key, Long>()

            for (i in 0 until template.length - 1) {
                val pair = Pair(template[i], template[i + 1])
                val last = i == template.length - 2
                increment(pairFrequencies, Key(pair, last), 1)
            }

            for (step in 0 until steps) {
                val next = mutableMapOf<Key, Long>()

                for ((key, frequency) in pairFrequencies) {
                    val pair = key.pair
                    val insertion = rules[pair] ?: throw UnsolvableException()

                    val key1 = Key(Pair(pair.first, insertion), false)
                    val key2 = Key(Pair(insertion, pair.second), key.last)

                    increment(next, key1, frequency)
                    increment(next, key2, frequency)
                }

                pairFrequencies = next
            }

            val charFrequencies = mutableMapOf<Char, Long>()

            for ((key, frequency) in pairFrequencies) {
                increment(charFrequencies, key.pair.first, frequency)

                if (key.last) {
                    increment(charFrequencies, key.pair.second, frequency)
                }
            }

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
