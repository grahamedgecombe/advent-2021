package com.grahamedgecombe.advent2021.day9

import kotlin.test.Test
import kotlin.test.assertEquals

object Day9Test {
    private val TEST_INPUT = Day9.parse("""
        2199943210
        3987894921
        9856789892
        8767896789
        9899965678
    """.trimIndent())
    private val PROD_INPUT = Day9.parse()

    @Test
    fun testPart1() {
        assertEquals(15, Day9.solvePart1(TEST_INPUT))
        assertEquals(631, Day9.solvePart1(PROD_INPUT))
    }
}
