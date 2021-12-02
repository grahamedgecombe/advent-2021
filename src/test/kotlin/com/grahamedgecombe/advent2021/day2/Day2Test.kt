package com.grahamedgecombe.advent2021.day2

import kotlin.test.Test
import kotlin.test.assertEquals

object Day2Test {
    private val TEST_INPUT = Day2.parse("""
        forward 5
        down 5
        forward 8
        up 3
        down 8
        forward 2
    """.trimIndent())
    private val PROD_INPUT = Day2.parse()

    @Test
    fun testPart1() {
        assertEquals(150, Day2.solvePart1(TEST_INPUT))
        assertEquals(1499229, Day2.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(900, Day2.solvePart2(TEST_INPUT))
        assertEquals(1340836560, Day2.solvePart2(PROD_INPUT))
    }
}
