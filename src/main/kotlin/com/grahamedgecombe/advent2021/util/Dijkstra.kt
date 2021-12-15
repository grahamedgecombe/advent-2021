package com.grahamedgecombe.advent2021.util

import java.util.PriorityQueue

object Dijkstra {
    interface Node<T : Node<T>> {
        val isGoal: Boolean
        val neighbours: Sequence<T>
        fun getDistance(neighbour: T): Int
    }

    fun <T : Node<T>> search(root: T): Sequence<List<T>> {
        val distance = mutableMapOf<T, Int>()
        val queue = PriorityQueue(compareBy(distance::get))
        val parents = mutableMapOf<T, T>()

        distance[root] = 0
        queue += root

        return sequence {
            while (true) {
                val current = queue.poll() ?: break
                if (current.isGoal) {
                    val path = mutableListOf<T>()

                    var node: T? = current
                    while (node != null) {
                        path += node
                        node = parents[node]
                    }

                    path.reverse()
                    yield(path)
                }

                for (neighbour in current.neighbours) {
                    val alt = distance[current]!! + current.getDistance(neighbour)
                    if (alt < distance.getOrDefault(neighbour, Int.MAX_VALUE)) {
                        distance[neighbour] = alt

                        queue -= neighbour
                        queue += neighbour

                        parents[neighbour] = current
                    }
                }
            }
        }
    }
}
