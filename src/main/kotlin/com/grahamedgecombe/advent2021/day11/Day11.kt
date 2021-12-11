package com.grahamedgecombe.advent2021.day11

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2

object Day11 : Puzzle<Day11.Grid>(11) {
    class Grid(
        val width: Int,
        val height: Int,
        private val energy: IntArray
    ) {
        fun copy(): Grid {
            return Grid(width, height, energy.copyOf())
        }

        fun step(): Int {
            for (i in energy.indices) {
                energy[i]++
            }

            val flashed = mutableSetOf<Vector2>()

            for (y in 0 until height) {
                for (x in 0 until width) {
                    tryFlash(x, y, flashed)
                }
            }

            for ((x, y) in flashed) {
                set(x, y, 0)
            }

            return flashed.size
        }

        private fun tryFlash(x: Int, y: Int, flashed: MutableSet<Vector2>) {
            val value = get(x, y)
            if (value <= 9) {
                return
            }

            val vector = Vector2(x, y)
            if (!flashed.add(vector)) {
                return
            }

            for (dy in -1..1) {
                val y0 = y + dy
                if (y0 !in 0 until height) {
                    continue
                }

                for (dx in -1..1) {
                    if (dx == 0 && dy == 0) {
                        continue
                    }

                    val x0 = x + dx
                    if (x0 !in 0 until width) {
                        continue
                    }

                    increment(x0, y0)
                    tryFlash(x0, y0, flashed)
                }
            }
        }

        private fun get(x: Int, y: Int): Int {
            val index = index(x, y)
            return energy[index]
        }

        private fun set(x: Int, y: Int, value: Int) {
            val index = index(x, y)
            energy[index] = value
        }

        private fun increment(x: Int, y: Int) {
            val index = index(x, y)
            energy[index]++
        }

        private fun index(x: Int, y: Int): Int {
            require(x in 0 until width)
            require(y in 0 until height)
            return width * y + x
        }

        companion object {
            fun parse(input: List<String>): Grid {
                val height = input.size
                val width = input.first().length

                val energy = IntArray(width * height)
                var index = 0
                for (row in input) {
                    for (c in row) {
                        energy[index++] = c.digitToInt()
                    }
                }

                return Grid(width, height, energy)
            }
        }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        val grid = input.copy()

        var flashes = 0
        for (i in 0 until 100) {
            flashes += grid.step()
        }
        return flashes
    }

    override fun solvePart2(input: Grid): Int {
        val grid = input.copy()

        var steps = 0
        do {
            steps++
        } while (grid.step() != (grid.width * grid.height))
        return steps
    }
}
