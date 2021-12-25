package com.grahamedgecombe.advent2021.day25

import kotlin.test.Test
import kotlin.test.assertEquals

object Day25Test {
    private val TEST_INPUT = Day25.parse("""
        v...>>.vv>
        .vv>>.vv..
        >>.>v>...v
        >>v>>.>.v.
        v>v.vv.v..
        >.>>..v...
        .vv..>.>v.
        v.v..>>v.v
        ....v..v.>
    """.trimIndent())
    private val PROD_INPUT = Day25.parse()

    @Test
    fun testPart1() {
        assertEquals(58, Day25.solvePart1(TEST_INPUT))
        assertEquals(321, Day25.solvePart1(PROD_INPUT))
    }
}
