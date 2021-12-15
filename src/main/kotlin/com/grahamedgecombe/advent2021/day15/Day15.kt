package com.grahamedgecombe.advent2021.day15

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException
import com.grahamedgecombe.advent2021.util.Dijkstra
import kotlin.math.abs

object Day15 : Puzzle<Day15.Grid>(15) {
    class Grid(
        val width: Int,
        val height: Int,
        private val risks: IntArray
    ) {
        fun get(x: Int, y: Int): Int {
            require(x in 0 until width)
            require(y in 0 until  height)

            val index = y * width + x
            return risks[index]
        }

        fun getLowestRiskPath(): Int {
            val path = Dijkstra.search(Node(this, 0, 0))
                .firstOrNull() ?: throw UnsolvableException()

            return path.asSequence()
                .drop(1)
                .sumOf { node -> get(node.x, node.y) }
        }

        fun expand(): Grid {
            val newWidth = width * 5
            val newHeight = height * 5
            val newRisks = IntArray(newWidth * newHeight)

            var srcIndex = 0
            for (srcY in 0 until height) {
                for (srcX in 0 until width) {
                    val risk = risks[srcIndex++]

                    for (dy in 0 until 5) {
                        for (dx in 0 until 5) {
                            var newRisk = risk + dx + dy
                            if (newRisk > 9) {
                                newRisk -= 9
                            }

                            val destY = srcY + dy * height
                            val destX = srcX + dx * height

                            val destIndex = destY * newWidth + destX
                            newRisks[destIndex] = newRisk
                        }
                    }
                }
            }

            return Grid(newWidth, newHeight, newRisks)
        }

        companion object {
            fun parse(input: List<String>): Grid {
                require(input.isNotEmpty())

                val width = input.first().length
                val height = input.size
                val risk = IntArray(width * height)

                var index = 0
                for (y in 0 until height) {
                    for (x in 0 until width) {
                        risk[index++] = input[y][x].digitToInt()
                    }
                }

                return Grid(width, height, risk)
            }
        }
    }

    data class Node(
        val grid: Grid,
        val x: Int,
        val y: Int,
    ) : Dijkstra.Node<Node> {
        override val isGoal: Boolean
            get() = x == (grid.width - 1) && y == (grid.height - 1)

        override val neighbours: Sequence<Node>
            get() = sequence {
                for (dy in -1..1) {
                    val y0 = y + dy
                    if (y0 < 0 || y0 >= grid.height) {
                        continue
                    }

                    for (dx in -1..1) {
                        if (abs(dx) == abs(dy)) {
                            continue
                        }

                        val x0 = x + dx
                        if (x0 < 0 || x0 >= grid.height) {
                            continue
                        }

                        yield(Node(grid, x0, y0))
                    }
                }
            }

        override fun getDistance(neighbour: Node): Int {
            return grid.get(neighbour.x, neighbour.y)
        }
    }

    override fun parse(input: Sequence<String>): Grid {
        return Grid.parse(input.toList())
    }

    override fun solvePart1(input: Grid): Int {
        return input.getLowestRiskPath()
    }

    override fun solvePart2(input: Grid): Int {
        return input.expand().getLowestRiskPath()
    }
}
