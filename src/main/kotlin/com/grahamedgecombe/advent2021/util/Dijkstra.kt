package com.grahamedgecombe.advent2021.util

import java.util.PriorityQueue

object Dijkstra {
    interface Node<T : Node<T>> {
        val isGoal: Boolean
        val neighbours: Sequence<Neighbour<T>>
    }

    data class Neighbour<T : Node<T>>(val node: T, val length: Int)
    data class Path<T : Node<T>>(val nodes: List<T>, val distance: Int)

    fun <T : Node<T>> search(root: T): Sequence<Path<T>> {
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
                    yield(Path(path, distance[current]!!))
                }

                for ((neighbour, length) in current.neighbours) {
                    val alt = distance[current]!! + length
                    if (alt < distance.getOrDefault(neighbour, Int.MAX_VALUE)) {
                        distance[neighbour] = alt

                        /*
                         * TODO(gpe): find an efficient way to remove an item
                         * from a PriorityQueue to reduce the number of nodes
                         * we have to visit.
                         */
                        queue += neighbour

                        parents[neighbour] = current
                    }
                }
            }
        }
    }
}
