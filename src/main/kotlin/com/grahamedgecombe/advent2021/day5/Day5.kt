package com.grahamedgecombe.advent2021.day5

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

object Day5 : Puzzle<List<Day5.Line>>(5) {
    data class Line(val source: Vector2, val destination: Vector2) {
        val horizontal: Boolean
            get() = source.y == destination.y

        val vertical: Boolean
            get() = source.x == destination.x

        val diagonal: Boolean
            get() = abs(source.x - destination.x) == abs(source.y - destination.y)

        companion object {
            private val REGEX = Regex("(\\d+),(\\d+) -> (\\d+),(\\d+)")

            fun parse(s: String): Line {
                val result = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (sourceX, sourceY, destinationX, destinationY) = result.destructured

                val source = Vector2(sourceX.toInt(), sourceY.toInt())
                val destination = Vector2(destinationX.toInt(), destinationY.toInt())

                return Line(source, destination)
            }
        }
    }

    class Grid(
        private val width: Int,
        private val height: Int
    ) {
        private val cells = IntArray(width * height)

        fun drawLine(line: Line, diagonal: Boolean) {
            if (line.horizontal) {
                val x1 = min(line.source.x, line.destination.x)
                val x2 = max(line.source.x, line.destination.x)
                drawHorizontalLine(x1, x2, line.source.y)
            } else if (line.vertical) {
                val y1 = min(line.source.y, line.destination.y)
                val y2 = max(line.source.y, line.destination.y)
                drawVerticalLine(line.source.x, y1, y2)
            } else if (diagonal && line.diagonal) {
                if (line.source.x > line.destination.x) {
                    // swap destination and source, so we only have to plot lines where x increases
                    drawLine(Line(line.destination, line.source), diagonal=true)
                    return
                }

                if (line.destination.y >= line.source.y) {
                    drawDiagonalLineDown(line.source.x, line.destination.x, line.source.y)
                } else {
                    drawDiagonalLineUp(line.source.x, line.destination.x, line.source.y)
                }
            }
        }

        private fun drawHorizontalLine(x1: Int, x2: Int, y: Int) {
            for (x in x1..x2) {
                val index = index(x, y)
                cells[index]++
            }
        }

        private fun drawVerticalLine(x: Int, y1: Int, y2: Int) {
            for (y in y1..y2) {
                val index = index(x, y)
                cells[index]++
            }
        }

        private fun drawDiagonalLineDown(x1: Int, x2: Int, y1: Int) {
            var y = y1
            for (x in x1..x2) {
                val index = index(x, y++)
                cells[index]++
            }
        }

        private fun drawDiagonalLineUp(x1: Int, x2: Int, y1: Int) {
            var y = y1
            for (x in x1..x2) {
                val index = index(x, y--)
                cells[index]++
            }
        }

        private fun index(x: Int, y: Int): Int {
            require(x in 0 until width)
            require(y in 0 until height)
            return y * width + x
        }

        fun countOverlaps(): Int {
            return cells.count { it >= 2 }
        }
    }

    override fun parse(input: Sequence<String>): List<Line> {
        return input.map(Line::parse).toList()
    }

    private fun solve(input: List<Line>, diagonal: Boolean): Int {
        val width = input.maxOf { it.source.x } + 1
        val height = input.maxOf { it.source.y } + 1

        val grid = Grid(width, height)
        for (line in input) {
            grid.drawLine(line, diagonal)
        }

        return grid.countOverlaps()
    }

    override fun solvePart1(input: List<Line>): Int {
        return solve(input, diagonal = false)
    }

    override fun solvePart2(input: List<Line>): Int {
        return solve(input, diagonal = true)
    }
}
