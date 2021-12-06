package com.grahamedgecombe.advent2021.day6

import kotlin.test.Test
import kotlin.test.assertEquals

object Day6Test {
    private val TEST_INPUT = Day6.parse("3,4,3,1,2")
    private val PROD_INPUT = Day6.parse()

    @Test
    fun testPart1() {
        assertEquals(5934, Day6.solvePart1(TEST_INPUT))
        assertEquals(390011, Day6.solvePart1(PROD_INPUT))
    }
}
