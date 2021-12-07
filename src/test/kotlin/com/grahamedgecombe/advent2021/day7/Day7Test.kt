package com.grahamedgecombe.advent2021.day7

import kotlin.test.Test
import kotlin.test.assertEquals

object Day7Test {
    private val TEST_INPUT = Day7.parse("16,1,2,0,4,2,7,1,2,14")
    private val PROD_INPUT = Day7.parse()

    @Test
    fun testPart1() {
        assertEquals(37, Day7.solvePart1(TEST_INPUT))
        assertEquals(359648, Day7.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(168, Day7.solvePart2(TEST_INPUT))
        assertEquals(100727924, Day7.solvePart2(PROD_INPUT))
    }
}
