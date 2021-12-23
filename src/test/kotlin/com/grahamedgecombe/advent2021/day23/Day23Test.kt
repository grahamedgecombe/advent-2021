package com.grahamedgecombe.advent2021.day23

import kotlin.test.Test
import kotlin.test.assertEquals

object Day23Test {
    private val TEST_INPUT = Day23.parse("""
        #############
        #...........#
        ###B#C#B#D###
          #A#D#C#A#
          #########
    """.trimIndent())
    private val PROD_INPUT = Day23.parse()

    @Test
    fun testPart1() {
        assertEquals(12521, Day23.solvePart1(TEST_INPUT))
        assertEquals(14371, Day23.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(44169, Day23.solvePart2(TEST_INPUT))
        assertEquals(40941, Day23.solvePart2(PROD_INPUT))
    }
}
