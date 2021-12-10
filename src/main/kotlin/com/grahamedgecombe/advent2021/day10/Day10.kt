package com.grahamedgecombe.advent2021.day10

import com.grahamedgecombe.advent2021.Puzzle

object Day10 : Puzzle<List<String>>(10) {
    private val OPENING_CHARS = setOf('(', '[', '{', '<')
    private val CLOSING_CHARS = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )
    private val CORRUPT_SCORE = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )
    private val INCOMPLETE_SCORE = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4,
    )

    override fun parse(input: Sequence<String>): List<String> {
        return input.toList()
    }

    private fun getScorePart1(line: String): Int {
        val stack = ArrayDeque<Char>()

        for (c in line) {
            if (c in OPENING_CHARS) {
                stack.addLast(c)
            } else if (CLOSING_CHARS[stack.removeLast()] != c) {
                return CORRUPT_SCORE[c]!!
            }
        }

        return 0
    }

    private fun getScorePart2(line: String): Long {
        val stack = ArrayDeque<Char>()

        for (c in line) {
            if (c in OPENING_CHARS) {
                stack.addLast(c)
            } else if (CLOSING_CHARS[stack.removeLast()] != c) {
                return 0
            }
        }

        var score = 0L
        while (true) {
            val c = stack.removeLastOrNull() ?: break
            score = (score * 5) + INCOMPLETE_SCORE[CLOSING_CHARS[c]!!]!!
        }
        return score
    }

    override fun solvePart1(input: List<String>): Int {
        return input.sumOf(::getScorePart1)
    }

    override fun solvePart2(input: List<String>): Long {
        val scores = input.map(::getScorePart2).filter { score -> score != 0L }.sorted()
        return scores[scores.size / 2]
    }
}
