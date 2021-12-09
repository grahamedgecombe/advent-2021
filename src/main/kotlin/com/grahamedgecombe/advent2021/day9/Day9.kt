package com.grahamedgecombe.advent2021.day9

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2

object Day9 : Puzzle<Day9.Grid>(9) {
    class Grid(
        private val width: Int,
        private val height: Int,
        private val heights: IntArray
    ) {
        private fun forEachLowPoint(f: (Int, Int, Int) -> Unit) {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    val height = get(x, y)

                    val top = get(x, y - 1)
                    val bottom = get(x, y + 1)
                    val left = get(x - 1, y)
                    val right = get(x + 1, y)

                    if (height < top && height < bottom && height < left && height < right) {
                        f(x, y, height)
                    }
                }
            }
        }

        private fun exploreBasin(x: Int, y: Int, height: Int, visited: MutableSet<Vector2>): Int {
            if (height == 9 || !visited.add(Vector2(x, y))) {
                return 0
            }

            var size = 1

            val top = get(x, y - 1)
            if (height < top) {
                size += exploreBasin(x, y - 1, top, visited)
            }

            val bottom = get(x, y + 1)
            if (height < bottom) {
                size += exploreBasin(x, y + 1, bottom, visited)
            }

            val left = get(x - 1, y)
            if (height < left) {
                size += exploreBasin(x - 1, y, left, visited)
            }

            val right = get(x + 1, y)
            if (height < right) {
                size += exploreBasin(x + 1, y, right, visited)
            }

            return size
        }

        fun sumLowPointRisk(): Int {
            var sum = 0

            forEachLowPoint { _, _, height ->
                sum += height + 1
            }

            return sum
        }

        fun productOfLargestBasins(): Int {
            val basins = mutableListOf<Int>()

            forEachLowPoint { x, y, height ->
                basins += exploreBasin(x, y, height, mutableSetOf())
            }

            return basins
                .sortedDescending()
                .take(3)
                .reduce(Int::times)
        }

        private fun get(x: Int, y: Int): Int {
            return if (x < 0 || x >= width || y < 0 || y >= height) {
                9
            } else {
                heights[y * width + x]
            }
        }

        companion object {
            fun parse(input: List<String>): Grid {
                val height = input.size
                val width = input.first().length

                val heights = IntArray(width * height)

                var index = 0
                for (row in input) {
                    for (c in row) {
                        heights[index++] = c.digitToInt()
                    }
                }

                return Grid(width, height, heights)
            }
        }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        return input.sumLowPointRisk()
    }

    override fun solvePart2(input: Grid): Int {
        return input.productOfLargestBasins()
    }
}
