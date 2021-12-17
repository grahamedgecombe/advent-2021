package com.grahamedgecombe.advent2021.day17

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2
import kotlin.math.max

object Day17 : Puzzle<Day17.TargetArea>(17) {
    data class TargetArea(val from: Vector2, val to: Vector2) {
        fun contains(position: Vector2): Boolean {
            return position.x in from.x..to.x && position.y in from.y..to.y
        }

        fun missedBy(position: Vector2, velocity: Vector2): Boolean {
            if (velocity.x == 0) {
                if (position.x !in from.x..to.x) {
                    return true
                }

                if (position.y < to.y) {
                    return true
                }
            }

            return position.x > to.x
        }

        companion object {
            private val REGEX = Regex("target area: x=(-?\\d+)[.][.](-?\\d+), y=(-?\\d+)[.][.](-?\\d+)")

            fun parse(s: String): TargetArea {
                val match = REGEX.matchEntire(s) ?: throw IllegalArgumentException()

                val (x0, x1, y0, y1) = match.destructured
                val from = Vector2(x0.toInt(), y0.toInt())
                val to = Vector2(x1.toInt(), y1.toInt())

                return TargetArea(from, to)
            }
        }
    }

    override fun parse(input: Sequence<String>): TargetArea {
        return TargetArea.parse(input.first())
    }

    private fun calculateMaxY(x: Int, y: Int, target: TargetArea): Int {
        var position = Vector2.ORIGIN
        var velocity = Vector2(x, y)
        var maxY = position.y

        while (true) {
            position += velocity
            velocity = Vector2(max(velocity.x - 1, 0), velocity.y - 1)
            maxY = max(maxY, position.y)

            if (target.contains(position)) {
                return maxY
            } else if (target.missedBy(position, velocity)) {
                return Int.MIN_VALUE
            }
        }
    }

    override fun solvePart1(input: TargetArea): Int {
        var maxY = Int.MIN_VALUE

        for (x in 0..input.to.x) {
            for (y in input.from.y..-input.from.y) {
                maxY = max(maxY, calculateMaxY(x, y, input))
            }
        }

        return maxY
    }

    override fun solvePart2(input: TargetArea): Int {
        var count = 0

        for (x in 0..input.to.x) {
            for (y in input.from.y..-input.from.y) {
                if (calculateMaxY(x, y, input) != Int.MIN_VALUE) {
                    count++
                }
            }
        }

        return count
    }
}
