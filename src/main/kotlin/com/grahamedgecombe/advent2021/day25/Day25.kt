package com.grahamedgecombe.advent2021.day25

import com.grahamedgecombe.advent2021.Puzzle

object Day25 : Puzzle<Day25.Grid>(25) {
    class Grid(
        private val width: Int,
        private val height: Int,
        private val tiles: CharArray
    ) {
        private fun step(type: Char, dx: Int, dy: Int): Grid {
            val newTiles = tiles.copyOf()

            for (y in 0 until height) {
                for (x in 0 until width) {
                    val index = y * width + x
                    val c = tiles[index]

                    val newIndex = if (c == type) {
                        val x0 = (x + dx) % width
                        val y0 = (y + dy) % height
                        y0 * width + x0
                    } else {
                        continue
                    }

                    if (tiles[newIndex] == '.') {
                        newTiles[newIndex] = c
                        newTiles[index] = '.'
                    }
                }
            }

            return Grid(width, height, newTiles)
        }

        fun step(): Grid {
            return step('>', 1, 0).step('v', 0, 1)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Grid

            if (width != other.width) return false
            if (height != other.height) return false
            if (!tiles.contentEquals(other.tiles)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = width
            result = 31 * result + height
            result = 31 * result + tiles.contentHashCode()
            return result
        }

        companion object {
            fun parse(rows: List<String>): Grid {
                require(rows.isNotEmpty())

                val width = rows.first().length
                val height = rows.size
                val tiles = CharArray(width * height)

                var index = 0
                for (row in rows) {
                    for (c in row) {
                        tiles[index++] = c
                    }
                }

                return Grid(width, height, tiles)
            }
        }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        var grid = input
        var steps = 1

        while (true) {
            val next = grid.step()
            if (next == grid) {
                return steps
            }

            grid = next
            steps++
        }
    }
}
