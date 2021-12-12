package com.grahamedgecombe.advent2021.day12

import com.grahamedgecombe.advent2021.Puzzle

object Day12 : Puzzle<Day12.Graph>(12) {
    class Graph(private val edges: Map<String, Set<String>>) {
        fun findPaths(): Set<List<String>> {
            return findPaths(emptyList(), "start")
        }

        private fun findPaths(path: List<String>, current: String): Set<List<String>> {
            if (isSmall(current) && path.contains(current)) {
                return emptySet()
            } else if (current == "end") {
                return setOf(path)
            }

            val paths = mutableSetOf<List<String>>()

            val neighbours = edges.getOrDefault(current, emptySet())
            for (neighbour in neighbours) {
                paths += findPaths(path + current, neighbour)
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
        return input.findPaths().size
    }
}
