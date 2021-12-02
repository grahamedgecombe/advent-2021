package com.grahamedgecombe.advent2021.util

class Vector3(val x: Int, val y: Int, val z: Int) {
    fun add(dx: Int, dy: Int, dz: Int): Vector3 {
        return Vector3(x + dx, y + dy, z + dz)
    }

    companion object {
        val ORIGIN = Vector3(0, 0, 0)
    }
}
