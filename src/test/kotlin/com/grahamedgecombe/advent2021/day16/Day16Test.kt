package com.grahamedgecombe.advent2021.day16

import kotlin.test.Test
import kotlin.test.assertEquals

object Day16Test {
    private val TEST_INPUT_1 = Day16.parse("D2FE28")
    private val TEST_INPUT_2 = Day16.parse("38006F45291200")
    private val TEST_INPUT_3 = Day16.parse("8A004A801A8002F478")
    private val TEST_INPUT_4 = Day16.parse("620080001611562C8802118E34")
    private val TEST_INPUT_5 = Day16.parse("C0015000016115A2E0802F182340")
    private val TEST_INPUT_6 = Day16.parse("A0016C880162017C3686B18A3D4780")
    private val PROD_INPUT = Day16.parse()

    @Test
    fun testPart1() {
        assertEquals(6, Day16.solvePart1(TEST_INPUT_1))
        assertEquals(9, Day16.solvePart1(TEST_INPUT_2))
        assertEquals(16, Day16.solvePart1(TEST_INPUT_3))
        assertEquals(12, Day16.solvePart1(TEST_INPUT_4))
        assertEquals(23, Day16.solvePart1(TEST_INPUT_5))
        assertEquals(31, Day16.solvePart1(TEST_INPUT_6))
        assertEquals(879, Day16.solvePart1(PROD_INPUT))
    }
}
