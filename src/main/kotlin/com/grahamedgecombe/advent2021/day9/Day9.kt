package com.grahamedgecombe.advent2021.day9

import com.grahamedgecombe.advent2021.Puzzle

object Day9 : Puzzle<Day9.Grid>(9) {
    class Grid(
        private val width: Int,
        private val height: Int,
        private val heights: IntArray
    ) {
        fun sumLowPointRisk(): Int {
            var sum = 0

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val height = get(x, y)

                    val top = get(x, y - 1)
                    val bottom = get(x, y + 1)
                    val left = get(x - 1, y)
                    val right = get(x + 1, y)

                    if (height < top && height < bottom && height < left && height < right) {
                        sum += height + 1
                    }
                }
            }

            return sum
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
}
