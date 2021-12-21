package com.grahamedgecombe.advent2021.day21

import com.grahamedgecombe.advent2021.Puzzle

object Day21 : Puzzle<Pair<Day21.Player, Day21.Player>>(21) {
    private fun parsePosition(s: String): Int {
        return s.split(':').lastOrNull()?.trim()?.toIntOrNull()
            ?: throw IllegalArgumentException()
    }

    override fun parse(input: Sequence<String>): Pair<Player, Player> {
        val players = input.toList()
        require(players.size == 2)

        val player1 = Player(parsePosition(players[0]) - 1, 0)
        val player2 = Player(parsePosition(players[1]) - 1, 0)

        return Pair(player1, player2)
    }

    class Die {
        private var current = 0
        var rolls = 0
            private set

        fun roll(): Int {
            var sum = 0
            for (i in 0 until 3) {
                sum += current + 1
                current = (current + 1) % 100
                rolls++
            }
            return sum
        }
    }

    data class Player(
        private val position: Int,
        val score: Int
    ) {
        fun move(roll: Int): Player {
            val next = (position + roll) % 10
            return Player(next, score + next + 1)
        }
    }

    override fun solvePart1(input: Pair<Player, Player>): Int {
        val die = Die()
        var player1 = input.first
        var player2 = input.second

        while (true) {
            player1 = player1.move(die.roll())
            if (player1.score >= 1000) {
                return player2.score * die.rolls
            }

            player2 = player2.move(die.roll())
            if (player2.score >= 1000) {
                return player1.score * die.rolls
            }
        }
    }
}
