package com.grahamedgecombe.advent2021.day12

import com.grahamedgecombe.advent2021.Puzzle

object Day12 : Puzzle<Day12.Graph>(12) {
    class Graph(private val edges: Map<String, Set<String>>) {
        private data class Key(val visited: Set<String>, val current: String, val allowDuplicate: Boolean)

        fun countPaths(allowDuplicate: Boolean): Int {
            return countPaths(mutableMapOf(), emptySet(), "start", allowDuplicate)
        }

        private fun countPaths(
            cache: MutableMap<Key, Int>,
            visited: Set<String>,
            current: String,
            allowDuplicate: Boolean
        ): Int {
            val key = Key(visited, current, allowDuplicate)

            val cachedPaths = cache[key]
            if (cachedPaths != null) {
                return cachedPaths
            }

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
            val neighbours = edges.getOrDefault(current, emptySet())
            for (neighbour in neighbours) {
                paths += countPaths(cache, visited.plus(current), neighbour, nextAllowDuplicate)
            }

            cache[key] = paths

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
