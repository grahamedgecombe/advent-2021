package com.grahamedgecombe.advent2021.util

import kotlin.math.abs

data class Vector3(val x: Int, val y: Int, val z: Int) {
    fun add(dx: Int, dy: Int, dz: Int): Vector3 {
        return Vector3(x + dx, y + dy, z + dz)
    }

    operator fun plus(o: Vector3): Vector3 {
        return Vector3(x + o.x, y + o.y, z + o.z)
    }

    operator fun minus(o: Vector3): Vector3 {
        return Vector3(x - o.x, y - o.y, z - o.z)
    }

    fun getManhattanDistance(o: Vector3): Int {
        return abs(x - o.x) + abs(y - o.y) + abs(z - o.z)
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    companion object {
        val ORIGIN = Vector3(0, 0, 0)
    }
}
