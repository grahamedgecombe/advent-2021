package com.grahamedgecombe.advent2021.day12

import com.grahamedgecombe.advent2021.Puzzle

object Day12 : Puzzle<Day12.Graph>(12) {
    class Graph(private val edges: Map<String, Set<String>>) {
        fun findPaths(allowDuplicate: Boolean): Set<List<String>> {
            return findPaths(emptyList(), "start", allowDuplicate)
        }

        private fun findPaths(path: List<String>, current: String, allowDuplicate: Boolean): Set<List<String>> {
            var nextAllowDuplicate = allowDuplicate

            if (current == "start") {
                if (path.isNotEmpty()) {
                    return emptySet()
                }
            } else if (current == "end") {
                return setOf(path)
            } else if (isSmall(current)) {
                val count = path.count { it == current }
                if (count >= 2) {
                    return emptySet()
                } else if (count == 1) {
                    if (allowDuplicate) {
                        nextAllowDuplicate = false
                    } else {
                        return emptySet()
                    }
                }
            }

            val paths = mutableSetOf<List<String>>()

            val neighbours = edges.getOrDefault(current, emptySet())
            for (neighbour in neighbours) {
                paths += findPaths(path + current, neighbour, nextAllowDuplicate)
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
        return input.findPaths(allowDuplicate = false).size
    }

    override fun solvePart2(input: Graph): Int {
        return input.findPaths(allowDuplicate = true).size
    }
}
