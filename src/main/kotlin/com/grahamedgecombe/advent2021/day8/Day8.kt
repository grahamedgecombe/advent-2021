package com.grahamedgecombe.advent2021.day8

import com.grahamedgecombe.advent2021.Puzzle

object Day8 : Puzzle<List<Day8.Entry>>(8) {
    data class Entry(val patterns: List<Set<Char>>, val output: List<Set<Char>>) {
        companion object {
            private val PIPE = Regex(" \\| ")

            fun parse(s: String): Entry {
                val (patterns, output) = s.split(PIPE, limit = 2)
                return Entry(toSets(patterns), toSets(output))
            }

            private fun toSets(s: String): List<Set<Char>> {
                return s.splitToSequence(' ')
                    .map(String::toSet)
                    .toList()
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Entry> {
        return input.map(Entry::parse).toList()
    }

    override fun solvePart1(input: List<Entry>): Int {
        return input.sumOf { entry ->
            entry.output.count { value ->
                value.size == 2 || value.size == 3 || value.size == 4 || value.size == 7
            }
        }
    }
}
