package com.grahamedgecombe.advent2021.day12

import com.grahamedgecombe.advent2021.Puzzle

object Day12 : Puzzle<Day12.Graph>(12) {
    class Graph(private val edges: Map<String, Set<String>>) {
        fun countPaths(allowDuplicate: Boolean): Int {
            return countPaths(mutableSetOf(), "start", allowDuplicate)
        }

        private fun countPaths(visited: MutableSet<String>, current: String, allowDuplicate: Boolean): Int {
            var nextAllowDuplicate = allowDuplicate

            if (current == "start") {
                if (visited.isNotEmpty()) {
                    return 0
                }
            } else if (current == "end") {
                return 1
            } else if (isSmall(current) && visited.contains(current)) {
                if (allowDuplicate) {
                    nextAllowDuplicate = false
                } else {
                    return 0
                }
            }

            var paths = 0

            /*
             * Remember if we've added current to the visited set at this stack
             * frame, so we don't remove the duplicate too early.
             */
            val added = visited.add(current)

            val neighbours = edges.getOrDefault(current, emptySet())
            for (neighbour in neighbours) {
                paths += countPaths(visited, neighbour, nextAllowDuplicate)
            }

            if (added) {
                visited.remove(current)
            }

            return paths
        }

        private fun isSmall(cave: String): Boolean {
            return cave.lowercase() == cave
        }
    }

    override fun parse(input: Sequence<String>): Graph {
        val edges = mutableMapOf<String, MutableSet<String>>()

        for (line in input) {
            val (a, b) = line.split('-', limit = 2)

            edges.computeIfAbsent(a) {
                mutableSetOf()
            }.add(b)

            edges.computeIfAbsent(b) {
                mutableSetOf()
            }.add(a)
        }

        return Graph(edges.mapValues { it.value })
    }

    override fun solvePart1(input: Graph): Int {
        return input.countPaths(allowDuplicate = false)
    }

    override fun solvePart2(input: Graph): Int {
        return input.countPaths(allowDuplicate = true)
    }
}
