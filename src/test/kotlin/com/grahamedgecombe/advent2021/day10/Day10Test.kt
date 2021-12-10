package com.grahamedgecombe.advent2021.day10

import kotlin.test.Test
import kotlin.test.assertEquals

object Day10Test {
    private val TEST_INPUT = Day10.parse("""
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
    """.trimIndent())
    private val PROD_INPUT = Day10.parse()

    @Test
    fun testPart1() {
        assertEquals(26397, Day10.solvePart1(TEST_INPUT))
        assertEquals(167379, Day10.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(288957, Day10.solvePart2(TEST_INPUT))
        assertEquals(2776842859, Day10.solvePart2(PROD_INPUT))
    }
}
