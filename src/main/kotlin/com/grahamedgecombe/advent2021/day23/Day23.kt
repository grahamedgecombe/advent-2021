package com.grahamedgecombe.advent2021.day23

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException
import com.grahamedgecombe.advent2021.util.Dijkstra
import kotlin.math.abs

object Day23 : Puzzle<Day23.Node>(23) {
    private const val HALLWAY_LENGTH = 11

    private val ENERGY = mutableMapOf(
        'A' to 1,
        'B' to 10,
        'C' to 100,
        'D' to 1000,
    )

    private val EXTRA_AMPHIPODS = arrayOf(
        charArrayOf('D', 'D'),
        charArrayOf('B', 'C'),
        charArrayOf('A', 'B'),
        charArrayOf('C', 'A'),
    )

    data class Node(
        val roomCapacity: Int,
        val hallway: CharArray,
        val rooms: Array<CharArray>,
    ) : Dijkstra.Node<Node> {
        override val isGoal: Boolean
            get() = rooms.withIndex().all { (i, room) -> isRoomInGoalState(i, room) }

        private fun isRoomInGoalState(roomIndex: Int, room: CharArray): Boolean {
            if (room.size != roomCapacity) {
                return false
            }

            for (amphipod in room) {
                if (amphipod != ('A' + roomIndex)) {
                    return false
                }
            }

            return true
        }

        private fun isBlocked(roomIndex: Int, hallwayIndex: Int, selfIndex: Int): Boolean {
            var a = roomIndex * 2 + 2
            var b = hallwayIndex

            if (a > b) {
                val temp = a
                a = b
                b = temp
            }

            for (i in a..b) {
                if (i != selfIndex && hallway[i] != ' ') {
                    return true
                }
            }

            return false
        }

        private fun isOutsideRoom(hallwayIndex: Int): Boolean {
            return hallwayIndex == 2 || hallwayIndex == 4 || hallwayIndex == 6 || hallwayIndex == 8
        }

        private fun getCost(roomIndex: Int, roomSize: Int, hallwayIndex: Int): Int {
            return abs((roomIndex * 2 + 2) - hallwayIndex) + (roomCapacity - roomSize) + 1
        }

        private fun isRoomEnterable(amphipod: Char, room: CharArray): Boolean {
            if (room.size >= roomCapacity) {
                return false
            }

            for (other in room) {
                if (amphipod != other) {
                    return false
                }
            }

            return true
        }

        override val neighbours: Sequence<Dijkstra.Neighbour<Node>>
            get() = sequence {
                // room => hallway
                for ((roomIndex, room) in rooms.withIndex()) {
                    if (isRoomInGoalState(roomIndex, room)) {
                        continue
                    }

                    val amphipod = room.lastOrNull() ?: continue

                    for ((hallwayIndex, dest) in hallway.withIndex()) {
                        if (dest != ' ' || isOutsideRoom(hallwayIndex) || isBlocked(roomIndex, hallwayIndex, -1)) {
                            continue
                        }

                        val newHallway = hallway.copyOf()
                        newHallway[hallwayIndex] = amphipod

                        val newRoom = room.copyOf(room.size - 1)

                        val newRooms = rooms.copyOf()
                        newRooms[roomIndex] = newRoom

                        val cost = getCost(roomIndex, room.size, hallwayIndex) * ENERGY[amphipod]!!
                        yield(Dijkstra.Neighbour(Node(roomCapacity, newHallway, newRooms), cost))
                    }
                }

                // hallway => room
                for ((hallwayIndex, amphipod) in hallway.withIndex()) {
                    if (amphipod == ' ') {
                        continue
                    }

                    val roomIndex = amphipod - 'A'
                    if (isBlocked(roomIndex, hallwayIndex, hallwayIndex)) {
                        continue
                    }

                    val room = rooms[roomIndex]
                    if (!isRoomEnterable(amphipod, room)) {
                        continue
                    }

                    val newHallway = hallway.copyOf()
                    newHallway[hallwayIndex] = ' '

                    val newRoom = room.copyOf(room.size + 1)
                    newRoom[newRoom.size - 1] = amphipod

                    val newRooms = rooms.copyOf()
                    newRooms[roomIndex] = newRoom

                    val cost = getCost(roomIndex, newRoom.size, hallwayIndex) * ENERGY[amphipod]!!
                    yield(Dijkstra.Neighbour(Node(roomCapacity, newHallway, newRooms), cost))
                }
            }

        fun unfold(): Node {
            require(roomCapacity == 2)

            val newRooms = Array(rooms.size) { i ->
                val newRoom = CharArray(4)

                newRoom[0] = rooms[i][0]
                newRoom[1] = EXTRA_AMPHIPODS[i][0]
                newRoom[2] = EXTRA_AMPHIPODS[i][1]
                newRoom[3] = rooms[i][1]

                newRoom
            }

            return Node(
                roomCapacity = 4,
                hallway,
                newRooms
            )
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Node

            if (roomCapacity != other.roomCapacity) return false
            if (!hallway.contentEquals(other.hallway)) return false
            if (!rooms.contentDeepEquals(other.rooms)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = roomCapacity
            result = 31 * result + hallway.contentHashCode()
            result = 31 * result + rooms.contentDeepHashCode()
            return result
        }

        override fun toString(): String {
            return "Node(roomCapacity=$roomCapacity, hallway=${hallway.contentToString()}, rooms=${rooms.contentDeepToString()})"
        }
    }

    override fun parse(input: Sequence<String>): Node {
        val grid = input.toList()

        return Node(
            2,
            CharArray(HALLWAY_LENGTH) { ' ' },
            arrayOf(
                charArrayOf(grid[3][3], grid[2][3]),
                charArrayOf(grid[3][5], grid[2][5]),
                charArrayOf(grid[3][7], grid[2][7]),
                charArrayOf(grid[3][9], grid[2][9]),
            )
        )
    }

    override fun solvePart1(input: Node): Int {
        val path = Dijkstra.search(input).firstOrNull() ?: throw UnsolvableException()
        return path.distance
    }

    override fun solvePart2(input: Node): Int {
        val path = Dijkstra.search(input.unfold()).firstOrNull() ?: throw UnsolvableException()
        return path.distance
    }
}
