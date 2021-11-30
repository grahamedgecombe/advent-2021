package com.grahamedgecombe.advent2021

import java.io.FileNotFoundException

abstract class Puzzle<T>(val number: Int) {
    fun parse(): T {
        return Puzzle::class.java.getResourceAsStream("day$number.txt").use { stream ->
            if (stream == null) {
                throw FileNotFoundException()
            }

            stream.bufferedReader().useLines(this::parse)
        }
    }

    fun parse(input: String): T {
        return parse(input.splitToSequence('\n'))
    }

    abstract fun parse(input: Sequence<String>): T
    abstract fun solvePart1(input: T): Any
    open fun solvePart2(input: T): Any? = null
}
