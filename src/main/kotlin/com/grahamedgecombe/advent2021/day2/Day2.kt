package com.grahamedgecombe.advent2021.day2

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.util.Vector2
import com.grahamedgecombe.advent2021.util.Vector3

object Day2 : Puzzle<List<Day2.Command>>(2) {
    sealed class Command {
        data class Forward(val x: Int) : Command()
        data class Down(val x: Int) : Command()
        data class Up(val x: Int) : Command()

        fun applyPart1(position: Vector2): Vector2 {
            return when (this) {
                is Forward -> position.add(x, 0)
                is Down -> position.add(0, x)
                is Up -> position.add(0, -x)
            }
        }

        fun applyPart2(position: Vector3): Vector3 {
            return when (this) {
                is Down -> position.add(0, 0, x)
                is Up -> position.add(0, 0, -x)
                is Forward -> position.add(x, x * position.z, 0)
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
            position = command.applyPart1(position)
        }
        return position.x * position.y
    }

    override fun solvePart2(input: List<Command>): Int {
        var position = Vector3.ORIGIN
        for (command in input) {
            position = command.applyPart2(position)
        }
        return position.x * position.y
    }
}
