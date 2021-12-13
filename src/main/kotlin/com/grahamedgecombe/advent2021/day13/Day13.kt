package com.grahamedgecombe.advent2021.day13

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2

object Day13 : Puzzle<Day13.Input>(13) {
    data class Input(
        val dots: Set<Vector2>,
        val folds: List<Fold>
    )

    enum class Axis {
        X,
        Y
    }

    data class Fold(
        val axis: Axis,
        val coordinate: Int
    ) {
        companion object {
            private val REGEX = Regex("fold along ([xy])=(\\d+)")

            fun parse(s: String): Fold {
                val match = REGEX.matchEntire(s) ?: throw IllegalArgumentException()
                val (axis, coordinate) = match.destructured
                return Fold(Axis.valueOf(axis.uppercase()), coordinate.toInt())
            }
        }
    }

    override fun parse(input: Sequence<String>): Input {
        val dots = mutableSetOf<Vector2>()
        val folds = mutableListOf<Fold>()

        var parseFolds = false

        for (s in input) {
            if (s.isEmpty()) {
                parseFolds = true
            } else if (parseFolds) {
                folds += Fold.parse(s)
            } else {
                val (x, y) = s.split(',', limit = 2)
                dots += Vector2(x.toInt(), y.toInt())
            }
        }

        return Input(dots, folds)
    }

    private fun fold(dot: Int, fold: Int): Int {
        return when {
            dot > fold -> fold - (dot - fold)
            dot == fold -> throw IllegalArgumentException()
            else -> dot
        }
    }

    private fun fold(dots: Set<Vector2>, fold: Fold): Set<Vector2> {
        return dots.mapTo(mutableSetOf()) { dot ->
            when (fold.axis) {
                Axis.X -> Vector2(fold(dot.x, fold.coordinate), dot.y)
                Axis.Y -> Vector2(dot.x, fold(dot.y, fold.coordinate))
            }
        }
    }

    private fun plot(dots: Set<Vector2>): String {
        val builder = StringBuilder()

        val width = dots.maxOf(Vector2::x) + 1
        val height = dots.maxOf(Vector2::y) + 1

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (dots.contains(Vector2(x, y))) {
                    builder.append('#')
                } else {
                    builder.append('.')
                }
            }

            if (y != height - 1) {
                builder.append('\n')
            }
        }

        return builder.toString()
    }

    override fun solvePart1(input: Input): Int {
        return fold(input.dots, input.folds.first()).size
    }

    override fun solvePart2(input: Input): String {
        var dots = input.dots
        for (fold in input.folds) {
            dots = fold(dots, fold)
        }
        return plot(dots)
    }
}
