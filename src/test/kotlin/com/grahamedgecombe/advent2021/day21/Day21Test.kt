package com.grahamedgecombe.advent2021.day21

import kotlin.test.Test
import kotlin.test.assertEquals

object Day21Test {
    private val TEST_INPUT = Day21.parse("""
        Player 1 starting position: 4
        Player 2 starting position: 8
    """.trimIndent())
    private val PROD_INPUT = Day21.parse()

    @Test
    fun testPart1() {
        assertEquals(739785, Day21.solvePart1(TEST_INPUT))
        assertEquals(805932, Day21.solvePart1(PROD_INPUT))
    }
}
