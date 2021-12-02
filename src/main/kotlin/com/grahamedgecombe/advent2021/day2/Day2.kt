package com.grahamedgecombe.advent2021.day2

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2

object Day2 : Puzzle<List<Day2.Command>>(2) {
    sealed class Command {
        data class Forward(val x: Int) : Command()
        data class Down(val x: Int) : Command()
        data class Up(val x: Int) : Command()

        fun apply(position: Vector2): Vector2 {
            return when (this) {
                is Forward -> position.add(x, 0)
                is Down -> position.add(0, x)
                is Up -> position.add(0, -x)
            }
        }

        companion object {
            fun parse(s: String): Command {
                val (command, operand) = s.split(' ', limit = 2)
                val x = operand.toInt()

                return when (command) {
                    "forward" -> Forward(x)
                    "down" -> Down(x)
                    "up" -> Up(x)
                    else -> throw IllegalArgumentException()
                }
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Command> {
        return input.map(Command.Companion::parse).toList()
    }

    override fun solvePart1(input: List<Command>): Int {
        var position = Vector2.ORIGIN
        for (command in input) {
            position = command.apply(position)
        }
        return position.x * position.y
    }
}
