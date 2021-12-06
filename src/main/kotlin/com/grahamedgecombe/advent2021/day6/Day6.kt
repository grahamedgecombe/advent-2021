package com.grahamedgecombe.advent2021.day6

import com.grahamedgecombe.advent2021.Puzzle

object Day6 : Puzzle<List<Int>>(6) {
    override fun parse(input: Sequence<String>): List<Int> {
        return input.first()
            .splitToSequence(',')
            .map(String::toInt)
            .toList()
    }

    override fun solvePart1(input: List<Int>): Int {
        val fish = input.toMutableList()

        for (day in 0 until 80) {
            for (i in 0 until fish.size) {
                val timer = fish[i]
                if (timer == 0) {
                    fish[i] = 6
                    fish += 8
                } else {
                    fish[i] = timer - 1
                }
            }
        }

        return fish.size
    }
}
