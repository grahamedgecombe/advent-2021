package com.grahamedgecombe.advent2021.day12

import kotlin.test.Test
import kotlin.test.assertEquals

object Day12Test {
    private val TEST_INPUT_1 = Day12.parse("""
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent())
    private val TEST_INPUT_2 = Day12.parse("""
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent())
    private val TEST_INPUT_3 = Day12.parse("""
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent())
    private val PROD_INPUT = Day12.parse()

    @Test
    fun testPart1() {
        assertEquals(10, Day12.solvePart1(TEST_INPUT_1))
        assertEquals(19, Day12.solvePart1(TEST_INPUT_2))
        assertEquals(226, Day12.solvePart1(TEST_INPUT_3))
        assertEquals(4338, Day12.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(36, Day12.solvePart2(TEST_INPUT_1))
        assertEquals(103, Day12.solvePart2(TEST_INPUT_2))
        assertEquals(3509, Day12.solvePart2(TEST_INPUT_3))
        assertEquals(114189, Day12.solvePart2(PROD_INPUT))
    }
}
