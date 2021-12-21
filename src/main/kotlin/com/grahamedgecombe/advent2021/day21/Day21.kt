package com.grahamedgecombe.advent2021.day21

import com.grahamedgecombe.advent2021.Puzzle
import kotlin.math.max

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

    data class State(val player1: Player, val player2: Player)

    private val DIRAC_ROLLS = buildList {
        for (roll1 in 1..3) {
            for (roll2 in 1..3) {
                for (roll3 in 1..3) {
                    add(roll1 + roll2 + roll3)
                }
            }
        }
    }

    private fun countWins(state: State, cache: MutableMap<State, Pair<Long, Long>>): Pair<Long, Long> {
        var pair = cache[state]
        if (pair != null) {
            return pair
        }

        var wins1 = 0L
        var wins2 = 0L

        for (roll in DIRAC_ROLLS) {
            val player1 = state.player1.move(roll)

            if (player1.score >= 21) {
                wins1++
            } else {
                pair = countWins(State(state.player2, player1), cache)
                wins1 += pair.second
                wins2 += pair.first
            }
        }

        pair = Pair(wins1, wins2)
        cache[state] = pair
        return pair
    }

    override fun solvePart2(input: Pair<Player, Player>): Long {
        val wins = countWins(State(input.first, input.second), mutableMapOf())
        return max(wins.first, wins.second)
    }
}
