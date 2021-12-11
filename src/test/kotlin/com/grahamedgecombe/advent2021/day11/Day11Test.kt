package com.grahamedgecombe.advent2021.day11

import kotlin.test.Test
import kotlin.test.assertEquals

object Day11Test {
    private val TEST_INPUT = Day11.parse("""
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
    """.trimIndent())
    private val PROD_INPUT = Day11.parse()

    @Test
    fun testPart1() {
        assertEquals(1656, Day11.solvePart1(TEST_INPUT))
        assertEquals(1675, Day11.solvePart1(PROD_INPUT))
    }
}
