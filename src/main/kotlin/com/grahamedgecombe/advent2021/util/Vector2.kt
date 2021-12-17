package com.grahamedgecombe.advent2021.util

data class Vector2(val x: Int, val y: Int) {
    fun add(dx: Int, dy: Int): Vector2 {
        return Vector2(x + dx, y + dy)
    }

    operator fun plus(v: Vector2): Vector2 {
        return add(v.x, v.y)
    }

    companion object {
        val ORIGIN = Vector2(0, 0)
    }
}
