package com.grahamedgecombe.advent2021.util

import java.util.PriorityQueue
import kotlin.math.min

object AStar {
    interface Node<T : Node<T>> {
        val isGoal: Boolean
        val neighbours: Sequence<Neighbour<T>>
        val cost: Int
    }

    data class Neighbour<T : Node<T>>(val node: T, val length: Int)
    data class Path<T : Node<T>>(val nodes: List<T>, val distance: Int)

    private fun saturatedAdd(a: Int, b: Int): Int {
        val sum = a.toLong() + b
        return min(sum, Int.MAX_VALUE.toLong()).toInt()
    }

    fun <T : Node<T>> search(root: T): Sequence<Path<T>> {
        val closed = mutableSetOf<T>()
        val open = mutableSetOf<T>()
        val parents = mutableMapOf<T, T>()
        val fScore = mutableMapOf<T, Int>()
        val gScore = mutableMapOf<T, Int>()

        val openQueue = PriorityQueue(compareBy<T> { node -> fScore.getOrDefault(node, Int.MAX_VALUE) })

        open += root
        openQueue += root
        fScore[root] = root.cost
        gScore[root] = 0

        return sequence {
            while (true) {
                val current = openQueue.poll() ?: break
                if (current.isGoal) {
                    val path = mutableListOf<T>()

                    var node: T? = current
                    while (node != null) {
                        path += node
                        node = parents[node]
                    }

                    path.reverse()
                    yield(Path(path, gScore[current]!!))
                }

                open -= current
                closed += current

                for ((neighbour, length) in current.neighbours) {
                    if (neighbour in closed) {
                        continue
                    }

                    val tentativeGScore = saturatedAdd(gScore.getOrDefault(current, Int.MAX_VALUE), length)
                    if (neighbour !in open) {
                        open += neighbour
                    } else if (tentativeGScore >= gScore.getOrDefault(neighbour, Int.MAX_VALUE)) {
                        continue
                    } else {
                        openQueue -= neighbour
                    }

                    parents[neighbour] = current
                    fScore[neighbour] = tentativeGScore + neighbour.cost
                    gScore[neighbour] = tentativeGScore

                    openQueue += neighbour
                }
            }
        }
    }
}
