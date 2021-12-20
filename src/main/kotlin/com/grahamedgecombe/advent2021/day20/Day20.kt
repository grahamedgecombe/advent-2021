package com.grahamedgecombe.advent2021.day20

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException
import com.grahamedgecombe.advent2021.util.Vector2
import kotlin.math.max
import kotlin.text.StringBuilder

object Day20 : Puzzle<Day20.Input>(20) {
    class Input(val enhancements: String, val image: Image) {
        companion object {
            fun parse(input: Sequence<String>): Input {
                val it = input.iterator()

                val enhancements = StringBuilder()
                while (it.hasNext()) {
                    val s = it.next()
                    if (s.isEmpty()) {
                        break
                    }

                    enhancements.append(s)
                }

                require(enhancements.length == 512)

                val pixels = mutableSetOf<Vector2>()
                var width = 0
                var y = 0
                while (it.hasNext()) {
                    val row = it.next()

                    for ((x, c) in row.withIndex()) {
                        if (c == '#') {
                            pixels += Vector2(x, y)
                        }
                    }

                    width = max(width, row.length)
                    y++
                }

                val xRange = 0 until width
                val yRange = 0 until y

                return Input(enhancements.toString(), Image(xRange, yRange, pixels, false))
            }
        }
    }

    class Image(
        private val xRange: IntRange,
        private val yRange: IntRange,
        private val pixels: Set<Vector2>,
        private val defaultLit: Boolean
    ) {
        fun next(enhancements: String): Image {
            val nextXRange = IntRange(this.xRange.first - 2, this.xRange.last + 2)
            val nextYRange = IntRange(this.yRange.first - 2, this.yRange.last + 2)
            val nextPixels = mutableSetOf<Vector2>()
            val nextDefaultLit = if (defaultLit) {
                enhancements[enhancements.length - 1] == '#'
            } else {
                enhancements[0] == '#'
            }

            for (y in nextYRange) {
                for (x in nextXRange) {
                    var pattern = 0

                    for (dy in -1..1) {
                        for (dx in -1..1) {
                            val bit = if (isLit(x + dx, y + dy)) 1 else 0
                            pattern = (pattern shl 1) or bit
                        }
                    }

                    if (enhancements[pattern] == '#') {
                        nextPixels += Vector2(x, y)
                    }
                }
            }

            return Image(nextXRange, nextYRange, nextPixels, nextDefaultLit)
        }

        private fun isLit(x: Int, y: Int): Boolean {
            return if (x in xRange && y in yRange) {
                pixels.contains(Vector2(x, y))
            } else {
                defaultLit
            }
        }

        fun countPixels(): Int {
            if (defaultLit) {
                throw UnsolvableException() // infinity!
            }

            return pixels.size
        }
    }

    override fun parse(input: Sequence<String>): Input {
        return Input.parse(input)
    }

    override fun solvePart1(input: Input): Int {
        return input.image
            .next(input.enhancements)
            .next(input.enhancements)
            .countPixels()
    }
}
