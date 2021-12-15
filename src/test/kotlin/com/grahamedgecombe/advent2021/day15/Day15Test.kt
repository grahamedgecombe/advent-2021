package com.grahamedgecombe.advent2021.day15

import kotlin.test.Test
import kotlin.test.assertEquals

object Day15Test {
    private val TEST_INPUT = Day15.parse("""
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent())
    private val PROD_INPUT = Day15.parse()

    @Test
    fun testPart1() {
        assertEquals(40, Day15.solvePart1(TEST_INPUT))
        assertEquals(824, Day15.solvePart1(PROD_INPUT))
    }
}
