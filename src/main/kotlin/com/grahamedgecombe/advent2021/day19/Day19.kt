package com.grahamedgecombe.advent2021.day19

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException
import com.grahamedgecombe.advent2021.util.Matrix3x3
import com.grahamedgecombe.advent2021.util.Vector3
import kotlin.math.max

object Day19 : Puzzle<List<Set<Vector3>>>(19) {
    private val SCANNER_PATTERN = Regex("--- scanner \\d+ ---")

    override fun parse(input: Sequence<String>): List<Set<Vector3>> {
        val scanners = mutableListOf<Set<Vector3>>()

        val it = input.iterator()
        while (it.hasNext()) {
            if (!SCANNER_PATTERN.matches(it.next())) {
                throw IllegalArgumentException()
            }

            val beacons = mutableSetOf<Vector3>()

            while (it.hasNext()) {
                val s = it.next()
                if (s.isEmpty()) {
                    break
                }

                val (x, y, z) = s.split(',', limit = 3)
                beacons += Vector3(x.toInt(), y.toInt(), z.toInt())
            }

            scanners += beacons
        }

        return scanners
    }

    private fun rotate(beacons: Set<Vector3>): Set<Set<Vector3>> {
        val rotations = mutableSetOf<Set<Vector3>>()
        var rotation = beacons

        for (x in 0 until 4) {
            rotation = rotation.mapTo(mutableSetOf()) { Matrix3x3.ROTATE_X_90 * it }

            for (y in 0 until 4) {
                rotation = rotation.mapTo(mutableSetOf()) { Matrix3x3.ROTATE_Y_90 * it }

                for (z in 0 until 4) {
                    rotation = rotation.mapTo(mutableSetOf()) { Matrix3x3.ROTATE_Z_90 * it }

                    rotations += rotation
                }
            }
        }

        return rotations
    }

    private fun getIntersectingPosition(existing: Set<Vector3>, new: Set<Vector3>): Vector3? {
        // iterate through all (existing, new) combinations to find every possible position relative to the origin
        for (a in existing) {
            for (b in new) {
                val position = b - a

                // check if at least 12 beacons intersect in this position
                var intersections = 0
                for (c in new) {
                    if (existing.contains(c - position) && ++intersections >= 12) {
                        return b - a
                    }
                }
            }
        }

        return null
    }

    override fun solvePart1(input: List<Set<Vector3>>): Int {
        val remaining = input.map(::rotate).toMutableList()

        val beacons = remaining.first().first().toMutableSet()
        val positions = mutableListOf<Vector3>()

        var removed: Boolean
        do {
            removed = remaining.removeIf { scanner ->
                for (rotation in scanner) {
                    val position = getIntersectingPosition(beacons, rotation) ?: continue

                    beacons += rotation.map { it - position }
                    positions += position

                    return@removeIf true
                }

                return@removeIf false
            }
        } while (removed)

        if (remaining.isNotEmpty()) {
            throw UnsolvableException()
        }

        return beacons.size
    }
}
