package com.grahamedgecombe.advent2021.day20

import kotlin.test.Test
import kotlin.test.assertEquals

object Day20Test {
    private val TEST_INPUT = Day20.parse("""
        ..#.#..#####.#.#.#.###.##.....###.##.#..###.####..#####..#....#..#..##..##
        #..######.###...####..#..#####..##..#.#####...##.#.#..#.##..#.#......#.###
        .######.###.####...#.##.##..#..#..#####.....#.#....###..#.##......#.....#.
        .#..#..##..#...##.######.####.####.#.#...#.......#..#.#.#...####.##.#.....
        .#..#...##.#.##..#...##.#.##..###.#......#.#.......#.#.#.####.###.##...#..
        ...####.#..#..#.##.#....##..#.####....##...##..#...#......#.#.......#.....
        ..##..####..#...#.#.#...##..#.#..###..#####........#..####......#..#

        #..#.
        #....
        ##..#
        ..#..
        ..###
    """.trimIndent())
    private val PROD_INPUT = Day20.parse()

    @Test
    fun testPart1() {
        assertEquals(35, Day20.solvePart1(TEST_INPUT))
        assertEquals(5464, Day20.solvePart1(PROD_INPUT))
    }

    @Test
    fun testPart2() {
        assertEquals(3351, Day20.solvePart2(TEST_INPUT))
        assertEquals(19228, Day20.solvePart2(PROD_INPUT))
    }
}
