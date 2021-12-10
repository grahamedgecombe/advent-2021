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
    private val SCORE = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )

    override fun parse(input: Sequence<String>): List<String> {
        return input.toList()
    }

    private fun getScore(line: String): Int {
        val stack = ArrayDeque<Char>()

        for (c in line) {
            if (c in OPENING_CHARS) {
                stack.addLast(c)
            } else if (CLOSING_CHARS[stack.removeLast()] != c) {
                return SCORE[c]!!
            }
        }

        return 0
    }

    override fun solvePart1(input: List<String>): Int {
        return input.sumOf(::getScore)
    }
}
