package com.grahamedgecombe.advent2021.day5

import kotlin.test.Test
import kotlin.test.assertEquals

object Day5Test {
    private val TEST_INPUT = Day5.parse("""
        0,9 -> 5,9
        8,0 -> 0,8
        9,4 -> 3,4
        2,2 -> 2,1
        7,0 -> 7,4
        6,4 -> 2,0
        0,9 -> 2,9
        3,4 -> 1,4
        0,0 -> 8,8
        5,5 -> 8,2
    """.trimIndent())
    private val PROD_INPUT = Day5.parse()

    @Test
    fun testPart1() {
        assertEquals(5, Day5.solvePart1(TEST_INPUT))
        assertEquals(5145, Day5.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(12, Day5.solvePart2(TEST_INPUT))
        assertEquals(16518, Day5.solvePart2(PROD_INPUT))
    }
}
