package com.grahamedgecombe.advent2021.day14

import kotlin.test.Test
import kotlin.test.assertEquals

object Day14Test {
    private val TEST_INPUT = Day14.parse("""
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent())
    private val PROD_INPUT = Day14.parse()

    @Test
    fun testPart1() {
        assertEquals(1588, Day14.solvePart1(TEST_INPUT))
        assertEquals(3259, Day14.solvePart1(PROD_INPUT))
    }
}
