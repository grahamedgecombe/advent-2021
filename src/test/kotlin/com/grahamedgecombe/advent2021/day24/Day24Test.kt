package com.grahamedgecombe.advent2021.day24

import kotlin.test.Test
import kotlin.test.assertEquals

object Day24Test {
    private val PROD_INPUT = Day24.parse()

    @Test
    fun testPart1() {
        assertEquals(98998519596997, Day24.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(31521119151421, Day24.solvePart2(PROD_INPUT))
    }
}
