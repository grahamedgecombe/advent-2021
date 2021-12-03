package com.grahamedgecombe.advent2021.day3

import kotlin.test.Test
import kotlin.test.assertEquals

object Day3Test {
    private val TEST_INPUT = Day3.parse("""
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent())
    private val PROD_INPUT = Day3.parse()

    @Test
    fun testPart1() {
        assertEquals(198, Day3.solvePart1(TEST_INPUT))
        assertEquals(4160394, Day3.solvePart1(PROD_INPUT))
    }
}
