package com.grahamedgecombe.advent2021.day1

import com.grahamedgecombe.advent2021.Puzzle

object Day1 : Puzzle<List<Int>>(1) {
    override fun parse(input: Sequence<String>): List<Int> {
        return input.map(String::toInt).toList()
    }

    override fun solvePart1(input: List<Int>): Int {
        return input.zipWithNext().count { (a, b) -> b > a }
    }
}
