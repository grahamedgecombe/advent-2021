package com.grahamedgecombe.advent2021.day22

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector3
import kotlin.math.max
import kotlin.math.min

object Day22 : Puzzle<List<Day22.Step>>(22) {
    data class Cube(val from: Vector3, val to: Vector3) {
        fun isInPart1Region(): Boolean {
            return from.x in -50..50 &&
                from.y in -50..50 &&
                from.z in -50..50 &&
                // our "to" is exclusive, unlike the problem input/description
                (to.x - 1) in -50..50 &&
                (to.y - 1) in -50..50 &&
                (to.z - 1) in -50..50
        }

        fun getVolume(): Long {
            return (to.x - from.x).toLong() * (to.y - from.y) * (to.z - from.z)
        }

        /**
         * Subtract the intersection of [other] and [this] from [this] and put
         * the remaining cubes in [destination].
         */
        fun minusTo(destination: MutableSet<Cube>, other: Cube) {
            if (
                from.x > other.to.x || to.x < other.from.x ||
                from.y > other.to.y || to.y < other.from.y ||
                from.z > other.to.z || to.z < other.from.z
            ) {
                destination += this
                return
            }

            // left
            if (from.x < other.from.x) {
                destination += Cube(Vector3(from.x, from.y, from.z), Vector3(other.from.x, to.y, to.z))
            }

            // right
            if (to.x > other.to.x) {
                destination += Cube(Vector3(other.to.x, from.y, from.z), Vector3(to.x, to.y, to.z))
            }

            // cut off the left/right from the remaining 4 cubes, so we don't count them again
            val x1 = max(from.x, other.from.x)
            val x2 = min(to.x, other.to.x)

            // bottom
            if (from.y < other.from.y) {
                destination += Cube(Vector3(x1, from.y, from.z), Vector3(x2, other.from.y, to.z))
            }

            // top
            if (to.y > other.to.y) {
                destination += Cube(Vector3(x1, other.to.y, from.z), Vector3(x2, to.y, to.z))
            }

            // cut off the top/bottom from the remaining 2 cubes, so we don't count them again
            val y1 = max(from.y, other.from.y)
            val y2 = min(to.y, other.to.y)

            // near
            if (from.z < other.from.z) {
                destination += Cube(Vector3(x1, y1, from.z), Vector3(x2, y2, other.from.z))
            }

            // far
            if (to.z > other.to.z) {
                destination += Cube(Vector3(x1, y1, other.to.z), Vector3(x2, y2, to.z))
            }
        }
    }

    data class Step(val on: Boolean, val cube: Cube) {
        companion object {
            private val REGEX = Regex("(on|off) x=(-?\\d+)[.][.](-?\\d+),y=(-?\\d+)[.][.](-?\\d+),z=(-?\\d+)[.][.](-?\\d+)")

            fun parse(s: String): Step {
                val match = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (onOff, xMin, xMax, yMin, yMax, zMin, zMax) = match.destructured

                val on = when (onOff) {
                    "on" -> true
                    "off" -> false
                    else -> throw IllegalArgumentException()
                }

                val from = Vector3(
                    xMin.toIntOrNull() ?: throw IllegalArgumentException(),
                    yMin.toIntOrNull() ?: throw IllegalArgumentException(),
                    zMin.toIntOrNull() ?: throw IllegalArgumentException(),
                )

                // making "to" exclusive simplifies bounds checks later
                val to = Vector3(
                    (xMax.toIntOrNull() ?: throw IllegalArgumentException()) + 1,
                    (yMax.toIntOrNull() ?: throw IllegalArgumentException()) + 1,
                    (zMax.toIntOrNull() ?: throw IllegalArgumentException()) + 1,
                )

                return Step(on, Cube(from, to))
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Step> {
        return input.map(Step::parse).toList()
    }

    private fun solve(steps: List<Step>): Long {
        var cubes = emptySet<Cube>()

        for (step in steps) {
            val next = mutableSetOf<Cube>()

            for (cube in cubes) {
                cube.minusTo(next, step.cube)
            }

            if (step.on) {
                next += step.cube
            }

            cubes = next
        }

        return cubes.sumOf(Cube::getVolume)
    }

    override fun solvePart1(input: List<Step>): Long {
        val steps = input.filter { step -> step.cube.isInPart1Region() }
        return solve(steps)
    }
}
