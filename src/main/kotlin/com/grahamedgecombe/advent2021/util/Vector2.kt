package com.grahamedgecombe.advent2021.util

data class Vector2(val x: Int, val y: Int) {
    fun add(dx: Int, dy: Int): Vector2 {
        return Vector2(x + dx, y + dy)
    }

    companion object {
        val ORIGIN = Vector2(0, 0)
    }
}
