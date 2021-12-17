package com.grahamedgecombe.advent2021.day17

import kotlin.test.Test
import kotlin.test.assertEquals

object Day17Test {
    private val TEST_INPUT = Day17.parse("target area: x=20..30, y=-10..-5")
    private val PROD_INPUT = Day17.parse()

    @Test
    fun testPart1() {
        assertEquals(45, Day17.solvePart1(TEST_INPUT))
        assertEquals(13041, Day17.solvePart1(PROD_INPUT))
    }
}
