package com.grahamedgecombe.advent2021.day1

import kotlin.test.Test
import kotlin.test.assertEquals

object Day1Test {
    private val TEST_INPUT = listOf(
        199,
        200,
        208,
        210,
        200,
        207,
        240,
        269,
        260,
        263,
    )
    private val PROD_INPUT = Day1.parse()

    @Test
    fun testPart1() {
        assertEquals(7, Day1.solvePart1(TEST_INPUT))
        assertEquals(1548, Day1.solvePart1(PROD_INPUT))
    }
}
