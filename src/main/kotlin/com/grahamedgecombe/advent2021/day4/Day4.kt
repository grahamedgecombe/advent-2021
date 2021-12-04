package com.grahamedgecombe.advent2021.day4

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException
import java.util.BitSet

object Day4 : Puzzle<Day4.Input>(4) {
    private val SPACES = Regex(" +")

    data class Input(
        val numbers: List<Int>,
        val boards: List<Board>
    )

    class Board (
        private val grid: IntArray
    ) {
        init {
            require(grid.size == SIZE * SIZE)
        }

        fun get(x: Int, y: Int): Int {
            val index = index(x, y)
            return grid[index]
        }

        companion object {
            const val SIZE = 5

            fun index(x: Int, y: Int): Int {
                require(x in 0 until SIZE)
                require(y in 0 until SIZE)
                return y * SIZE + x
            }
        }
    }

    class MarkedBoard(
        private val board: Board
    ) {
        private val marks = BitSet(Board.SIZE * Board.SIZE)

        fun mark(value: Int) {
            for (y in 0 until Board.SIZE) {
                for (x in 0 until Board.SIZE) {
                    if (board.get(x, y) == value) {
                        marks.set(y * Board.SIZE + x)
                    }
                }
            }
        }

        fun isWinner(): Boolean {
            for (x in 0 until Board.SIZE) {
                var count = 0

                for (y in 0 until Board.SIZE) {
                    val index = Board.index(x, y)
                    if (marks.get(index)) {
                        count++
                    }
                }

                if (count == Board.SIZE) {
                    return true
                }
            }


            for (y in 0 until Board.SIZE) {
                var count = 0

                for (x in 0 until Board.SIZE) {
                    val index = Board.index(x, y)
                    if (marks.get(index)) {
                        count++
                    }
                }

                if (count == Board.SIZE) {
                    return true
                }
            }

            return false
        }

        fun sumUnmarked(): Int {
            var sum = 0

            for (y in 0 until Board.SIZE) {
                for (x in 0 until Board.SIZE) {
                    val index = Board.index(x, y)
                    if (!marks.get(index)) {
                        sum += board.get(x, y)
                    }
                }
            }

            return sum
        }
    }

    override fun parse(input: Sequence<String>): Input {
        val it = input.iterator()
        require(it.hasNext())

        val numbers = it.next().splitToSequence(',').map(String::toInt).toList()
        val boards = mutableListOf<Board>()

        while (it.hasNext()) {
            require(it.next().isEmpty())

            val grid = IntArray(Board.SIZE * Board.SIZE)
            var index = 0

            for (y in 0 until Board.SIZE) {
                val row = it.next()
                    .splitToSequence(SPACES)
                    .filter { it.isNotEmpty() }
                    .map { it.trim().toInt() }

                for (value in row) {
                    grid[index++] = value
                }
            }

            boards += Board(grid)
        }

        return Input(numbers, boards)
    }

    override fun solvePart1(input: Input): Int {
        val boards = input.boards.map(::MarkedBoard)

        for (number in input.numbers) {
            for (board in boards) {
                board.mark(number)

                if (board.isWinner()) {
                    return number * board.sumUnmarked()
                }
            }
        }

        throw UnsolvableException()
    }

    override fun solvePart2(input: Input): Int {
        val boards = input.boards.map(::MarkedBoard).toMutableList()
        var score: Int? = null

        for (number in input.numbers) {
            boards.removeIf { board ->
                board.mark(number)

                val winner = board.isWinner()
                if (winner) {
                    score = number * board.sumUnmarked()
                }

                winner
            }
        }

        return score ?: throw UnsolvableException()
    }
}
