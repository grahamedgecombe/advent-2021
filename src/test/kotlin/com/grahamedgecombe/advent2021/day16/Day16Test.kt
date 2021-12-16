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
    private val TEST_INPUT_7 = Day16.parse("C200B40A82")
    private val TEST_INPUT_8 = Day16.parse("04005AC33890")
    private val TEST_INPUT_9 = Day16.parse("880086C3E88112")
    private val TEST_INPUT_10 = Day16.parse("CE00C43D881120")
    private val TEST_INPUT_11 = Day16.parse("D8005AC2A8F0")
    private val TEST_INPUT_12 = Day16.parse("F600BC2D8F")
    private val TEST_INPUT_13 = Day16.parse("9C005AC2F8F0")
    private val TEST_INPUT_14 = Day16.parse("9C0141080250320F1802104A08")
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

    @Test
    fun testPart2() {
        assertEquals(3, Day16.solvePart2(TEST_INPUT_7))
        assertEquals(54, Day16.solvePart2(TEST_INPUT_8))
        assertEquals(7, Day16.solvePart2(TEST_INPUT_9))
        assertEquals(9, Day16.solvePart2(TEST_INPUT_10))
        assertEquals(1, Day16.solvePart2(TEST_INPUT_11))
        assertEquals(0, Day16.solvePart2(TEST_INPUT_12))
        assertEquals(0, Day16.solvePart2(TEST_INPUT_13))
        assertEquals(1, Day16.solvePart2(TEST_INPUT_14))
        assertEquals(539051801941, Day16.solvePart2(PROD_INPUT))
    }
}
