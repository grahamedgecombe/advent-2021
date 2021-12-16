package com.grahamedgecombe.advent2021.day16

import com.grahamedgecombe.advent2021.Puzzle
import kotlin.math.min

object Day16 : Puzzle<Day16.Packet>(16) {
    sealed class Packet {
        abstract val version: Int

        data class Literal(override val version: Int, val value: Long) : Packet()
        data class Operator(override val version: Int, val type: Int, val children: List<Packet>) : Packet()

        fun sumVersions(): Int {
            var sum = version
            if (this is Operator) {
                sum += children.sumOf(Packet::sumVersions)
            }
            return sum
        }

        fun evaluate(): Long {
            return when (this) {
                is Literal -> value
                is Operator -> {
                    val values = children.map(Packet::evaluate)
                    when (type) {
                        0 -> values.reduce(Long::plus)
                        1 -> values.reduce(Long::times)
                        2 -> values.minOrNull() ?: throw IllegalArgumentException()
                        3 -> values.maxOrNull() ?: throw IllegalArgumentException()
                        5 -> if (values[0] > values[1]) 1 else 0
                        6 -> if (values[0] < values[1]) 1 else 0
                        7 -> if (values[0] == values[1]) 1 else 0
                        else -> throw AssertionError()
                    }
                }
            }
        }

        companion object {
            fun parse(reader: BitReader): Packet {
                val version = reader.readBits(3)
                val type = reader.readBits(3)

                if (type == 4) {
                    var value = 0L

                    do {
                        val more = reader.readBits(1)
                        val nibble = reader.readBits(4)

                        value = (value shl 4) or nibble.toLong()
                    } while (more != 0)

                    return Literal(version, value)
                }

                val children = mutableListOf<Packet>()

                val lengthType = reader.readBits(1)
                if (lengthType == 0) {
                    val bits = reader.readBits(15)

                    val end = reader.position + bits
                    while (reader.position < end) {
                        children += parse(reader)
                    }
                } else {
                    val n = reader.readBits(11)
                    for (i in 0 until n) {
                        children += parse(reader)
                    }
                }

                return Operator(version, type, children)
            }
        }
    }

    class BitReader constructor(
        private val bytes: ByteArray
    ) {
        var position: Int = 0
            private set

        fun readBits(len: Int): Int {
            require(len in 1..32)

            var value = 0

            var remaining = len
            var byteIndex = position shr 3
            var bitIndex = position and 7

            while (remaining > 0) {
                val n = min(8 - bitIndex, remaining)
                val shift = (8 - (bitIndex + n)) and 7
                val mask = (1 shl n) - 1

                val v = bytes[byteIndex].toInt() and 0xFF
                value = value shl n
                value = value or ((v shr shift) and mask)

                remaining -= n
                byteIndex++
                bitIndex = 0
            }

            position += len

            return value
        }
    }

    private fun decodeHex(s: String): ByteArray {
        return ByteArray(s.length / 2) { i ->
            val digit1 = s[i * 2].digitToInt(16)
            val digit2 = s[i * 2 + 1].digitToInt(16)
            ((digit1 shl 4) or digit2).toByte()
        }
    }

    override fun parse(input: Sequence<String>): Packet {
        val reader = BitReader(decodeHex(input.first()))
        return Packet.parse(reader)
    }

    override fun solvePart1(input: Packet): Int {
        return input.sumVersions()
    }

    override fun solvePart2(input: Packet): Long {
        return input.evaluate()
    }
}
