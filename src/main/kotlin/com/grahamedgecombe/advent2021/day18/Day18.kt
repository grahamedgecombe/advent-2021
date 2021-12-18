package com.grahamedgecombe.advent2021.day18

import com.grahamedgecombe.advent2021.Puzzle
import java.io.Reader
import java.io.StringReader
import kotlin.math.max

object Day18 : Puzzle<List<Day18.Cell>>(18) {
    data class ExplodeResult(val cell: Cell, val leftResidual: Int, val rightResidual: Int)

    sealed class Cell {
        data class Cons(val left: Cell, val right: Cell) : Cell() {
            override fun getMagnitude(): Int {
                return (3 * left.getMagnitude()) + (2 * right.getMagnitude())
            }

            override fun explode(depth: Int): ExplodeResult? {
                if (depth >= 4 && left is Atom && right is Atom) {
                    return ExplodeResult(Atom(0), left.value, right.value)
                }

                var result = left.explode(depth + 1)
                if (result != null) {
                    val cell = Cons(result.cell, right.addLeftmost(result.rightResidual))
                    return ExplodeResult(cell, result.leftResidual, 0)
                }

                result = right.explode(depth + 1)
                if (result != null) {
                    val cell = Cons(left.addRightmost(result.leftResidual), result.cell)
                    return ExplodeResult(cell, 0, result.rightResidual)
                }

                return null
            }

            override fun split(): Cell? {
                val splitLeft = left.split()
                if (splitLeft != null) {
                    return Cons(splitLeft, right)
                }

                val splitRight = right.split()
                if (splitRight != null) {
                    return Cons(left, splitRight)
                }

                return null
            }

            override fun addLeftmost(n: Int): Cell {
                return if (n == 0) {
                    this
                } else {
                    Cons(left.addLeftmost(n), right)
                }
            }

            override fun addRightmost(n: Int): Cell {
                return if (n == 0) {
                    this
                } else {
                    Cons(left, right.addRightmost(n))
                }
            }

            override fun toString(): String {
                return "[$left,$right]"
            }
        }

        data class Atom(val value: Int) : Cell() {
            override fun getMagnitude(): Int {
                return value
            }

            override fun explode(depth: Int): ExplodeResult? {
                return null
            }

            override fun split(): Cell? {
                if (value >= 10) {
                    return Cons(Atom(value / 2), Atom((value + 1) / 2))
                }

                return null
            }

            override fun addLeftmost(n: Int): Cell {
                return if (n == 0) {
                    this
                } else {
                    Atom(value + n)
                }
            }

            override fun addRightmost(n: Int): Cell {
                return addLeftmost(n)
            }

            override fun toString(): String {
                return value.toString()
            }
        }

        abstract fun getMagnitude(): Int
        abstract fun explode(depth: Int): ExplodeResult?
        abstract fun split(): Cell?
        abstract fun addLeftmost(n: Int): Cell
        abstract fun addRightmost(n: Int): Cell

        fun explode(): Cell? {
            return explode(0)?.cell
        }

        fun plus(other: Cell): Cell {
            return Cons(this, other).reduce()
        }

        private fun reduceOnce(): Cell? {
            val explode = explode()
            if (explode != null) {
                return explode
            }

            val split = split()
            if (split != null) {
                return split
            }

            return null
        }

        fun reduce(): Cell {
            var current = this

            while (true) {
                val reduced = current.reduceOnce() ?: break
                current = reduced
            }

            return current
        }

        companion object {
            fun parse(s: String): Cell {
                return StringReader(s).use { reader ->
                    parse(reader)
                }
            }

            fun parse(reader: Reader): Cell {
                val ch = reader.read()
                require(ch != -1)

                return if (ch == '['.code) {
                    val left = parse(reader)
                    require(reader.read() == ','.code)

                    val right = parse(reader)
                    require(reader.read() == ']'.code)

                    Cons(left, right)
                } else {
                    Atom(ch.toChar().digitToInt())
                }
            }
        }
    }

    fun sumOf(input: List<Cell>): Cell {
        return input.reduce(Cell::plus)
    }

    override fun parse(input: Sequence<String>): List<Cell> {
        return input.map(Cell::parse).toList()
    }

    override fun solvePart1(input: List<Cell>): Int {
        return sumOf(input).getMagnitude()
    }

    override fun solvePart2(input: List<Cell>): Int {
        var magnitude = Int.MIN_VALUE

        for (a in input) {
            for (b in input) {
                if (a == b) {
                    continue
                }

                magnitude = max(magnitude, a.plus(b).getMagnitude())
            }
        }

        return magnitude
    }
}
