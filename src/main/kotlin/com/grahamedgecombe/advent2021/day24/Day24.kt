package com.grahamedgecombe.advent2021.day24

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

object Day24 : Puzzle<Day24.Program>(24) {
    sealed class Operand {
        data class Variable(val index: Int) : Operand() {
            companion object {
                fun parse(s: String): Variable {
                    return when (s) {
                        "w" -> Variable(0)
                        "x" -> Variable(1)
                        "y" -> Variable(2)
                        "z" -> Variable(3)
                        else -> throw IllegalArgumentException()
                    }
                }
            }

            override fun evaluate(vm: VirtualMachine): Int {
                return vm.registers[index]
            }
        }

        data class Number(val value: Int) : Operand() {
            override fun evaluate(vm: VirtualMachine): Int {
                return value
            }
        }

        abstract fun evaluate(vm: VirtualMachine): Int

        companion object {
            fun parse(s: String): Operand {
                val value = s.toIntOrNull()
                return if (value != null) {
                    Number(value)
                } else {
                    Variable.parse(s)
                }
            }
        }
    }

    sealed class Instruction {
        data class Input(val a: Operand.Variable) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                val value = input.removeFirstOrNull() ?: return false
                vm.registers[a.index] = value
                return true
            }
        }

        data class Add(val a: Operand.Variable, val b: Operand) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                vm.registers[a.index] = a.evaluate(vm) + b.evaluate(vm)
                return true
            }
        }

        data class Mul(val a: Operand.Variable, val b: Operand) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                vm.registers[a.index] = a.evaluate(vm) * b.evaluate(vm)
                return true
            }
        }

        data class Div(val a: Operand.Variable, val b: Operand) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                vm.registers[a.index] = a.evaluate(vm) / b.evaluate(vm)
                return true
            }
        }

        data class Mod(val a: Operand.Variable, val b: Operand) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                vm.registers[a.index] = a.evaluate(vm) % b.evaluate(vm)
                return true
            }
        }

        data class Eql(val a: Operand.Variable, val b: Operand) : Instruction() {
            override fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean {
                vm.registers[a.index] = if (a.evaluate(vm) == b.evaluate(vm)) 1 else 0
                return true
            }
        }

        abstract fun evaluate(vm: VirtualMachine, input: ArrayDeque<Int>): Boolean

        companion object {
            fun parse(s: String): Instruction {
                val parts = s.split(' ')
                require(parts.size == 2 || parts.size == 3)

                val opcode = parts[0]
                val a = Operand.Variable.parse(parts[1])

                if (opcode == "inp") {
                    return Input(a)
                } else {
                    require(parts.size == 3)

                    val b = Operand.parse(parts[2])

                    return when (opcode) {
                        "add" -> Add(a, b)
                        "mul" -> Mul(a, b)
                        "div" -> Div(a, b)
                        "mod" -> Mod(a, b)
                        "eql" -> Eql(a, b)
                        else -> throw IllegalArgumentException()
                    }
                }
            }
        }
    }

    data class Program(val instructions: List<Instruction>) {
        companion object {
            fun parse(input: Sequence<String>): Program {
                return Program(input.map(Instruction::parse).toList())
            }
        }
    }

    class VirtualMachine(
        private val program: Program,
        val registers: IntArray = IntArray(4),
        private var pc: Int = 0
    ) {
        fun copy(): VirtualMachine {
            return VirtualMachine(program, registers.copyOf(), pc)
        }

        fun evaluate(input: Int): Boolean {
            val queue = ArrayDeque<Int>()
            queue += input

            while (pc < program.instructions.size) {
                val instruction = program.instructions[pc]
                if (!instruction.evaluate(this, queue)) {
                    return true
                }
                pc++
            }

            return false
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as VirtualMachine

            if (program != other.program) return false
            if (!registers.contentEquals(other.registers)) return false
            if (pc != other.pc) return false

            return true
        }

        override fun hashCode(): Int {
            var result = program.hashCode()
            result = 31 * result + registers.contentHashCode()
            result = 31 * result + pc
            return result
        }
    }

    override fun parse(input: Sequence<String>): Program {
        return Program.parse(input)
    }

    private fun solve(
        vm: VirtualMachine,
        digits: IntProgression,
        visited: MutableSet<VirtualMachine>,
        code: Long
    ): Long? {
        if (!visited.add(vm)) {
            return null
        }

        for (digit in digits) {
            val nextCode = code * 10 + digit
            val nextVm = vm.copy()

            if (nextVm.evaluate(digit)) {
                /*
                 * After each input instruction w, x and y are reset before
                 * being used (w by the input instruction itself, x by
                 * `mul x 0` and y by `mul y 0`). Reset them here to reduce the
                 * number of states in the visited set.
                 */
                for (i in 0..2) {
                    nextVm.registers[i] = 0
                }

                /*
                 * The subprogram for each input digit can be simplified to:
                 *
                 *     x = z % 26 + A
                 *
                 *     if (B) {
                 *         z /= 26
                 *     }
                 *
                 *     w = input()
                 *     if (x != w) {
                 *         z *= 26
                 *         z += w + C
                 *     }
                 *
                 * where A, B and C are constants.
                 *
                 * z is therefore a base26-encoded stack!
                 *
                 * When z is zero, the stack is empty. Therefore, in my
                 * particular input, the sequence of pushes and pops to reach
                 * zero must be:
                 *
                 * push, push, push, push, pop, pop, push, pop, push, pop, push, pop, pop, pop
                 *
                 * The maximum height of the stack for a valid solution is
                 * therefore 4 items, so we can exclude any states where z has
                 * greater than 4 items.
                 */
                if (nextVm.registers[3] > 26 * 26 * 26 * 26) {
                    continue
                }

                val solution = solve(nextVm.copy(), digits, visited, nextCode)
                if (solution != null) {
                    return solution
                }
            } else {
                val z = nextVm.registers[3]
                if (z == 0) {
                    return nextCode
                }
            }
        }

        return null
    }

    private fun solve(input: Program, digits: IntProgression): Long {
        return solve(VirtualMachine(input), digits, mutableSetOf(), 0) ?: throw UnsolvableException()
    }

    override fun solvePart1(input: Program): Long {
        return solve(input, 9 downTo 1)
    }

    override fun solvePart2(input: Program): Long {
        return solve(input, 1..9)
    }
}
