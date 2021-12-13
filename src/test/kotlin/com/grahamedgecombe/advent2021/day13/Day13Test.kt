package com.grahamedgecombe.advent2021.day13

import kotlin.test.Test
import kotlin.test.assertEquals

object Day13Test {
    private val TEST_INPUT = Day13.parse("""
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent())
    private val PROD_INPUT = Day13.parse()

    @Test
    fun testPart1() {
        assertEquals(17, Day13.solvePart1(TEST_INPUT))
        assertEquals(850, Day13.solvePart1(PROD_INPUT))
    }
}
