package com.grahamedgecombe.advent2021.day8

import com.grahamedgecombe.advent2021.Puzzle
import com.grahamedgecombe.advent2021.UnsolvableException

object Day8 : Puzzle<List<Day8.Entry>>(8) {
    private val ALL_SEGMENTS = ('a'..'g').toSet()
    private val DIGITS = mapOf(
        setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
        setOf('c', 'f') to 1,
        setOf('a', 'c', 'd', 'e', 'g') to 2,
        setOf('a', 'c', 'd', 'f', 'g') to 3,
        setOf('b', 'c', 'd', 'f') to 4,
        setOf('a', 'b', 'd', 'f', 'g') to 5,
        setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
        setOf('a', 'c', 'f') to 7,
        setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
        setOf('a', 'b', 'c', 'd', 'f', 'g') to 9,
    )

    data class Entry(val patterns: List<Set<Char>>, val output: List<Set<Char>>) {
        fun solve(): Int {
            /*
             * Create map of segments to candidate wires. Initially each
             * segment has all wires as candidates, we reduce the sizes of the
             * candidate sets below.
             */
            val candidates = mutableMapOf<Char, MutableSet<Char>>()
            for (segment in ALL_SEGMENTS) {
                candidates[segment] = ALL_SEGMENTS.toMutableSet()
            }

            for (pattern in patterns) {
                /*
                 * Find possible digits for the current pattern, by looking for
                 * segment sets of the same size.
                 */
                val possibleDigits = DIGITS.keys.filter { it.size == pattern.size }

                /*
                 * Find segments always turned on and always turned off in the
                 * set of possible digits.
                 */
                val alwaysOn = possibleDigits.reduce(Set<Char>::intersect)
                val alwaysOff = ALL_SEGMENTS - possibleDigits.reduce(Set<Char>::union)

                /*
                 * If a segment is always on, the wire for that segment must
                 * be in the pattern.
                 *
                 * We can therefore rule out candidate wires that are not in
                 * the pattern.
                 */
                for (segment in alwaysOn) {
                    candidates[segment]!!.removeIf { wire -> !pattern.contains(wire) }
                }

                /*
                 * If a segment is always off, the wire for that segment must
                 * not be in the pattern.
                 *
                 * We can therefore rule out candidate wires that are in the
                 * pattern.
                 */
                for (segment in alwaysOff) {
                    candidates[segment]!!.removeIf { wire -> pattern.contains(wire) }
                }
            }

            /*
             * At this point we end up with a segment to candidate wire mapping
             * like:
             *
             * {a=[d], b=[e], c=[a, b], d=[f], e=[c, g], f=[b], g=[c]}
             *
             * We can remove the final ambiguities by first finding singleton
             * candidate wire sets (in this case [d], [e], [f], [b] and [c]),
             * for which the segment <=> wire mapping is now certain, and
             * removing these wires fom the ambiguous sets, as we can now rule
             * those wires out of the ambiguous sets.
             *
             * Repeat until we reach a fixed point (in the example data this
             * isn't required, but I'm not sure if it will work without this in
             * all cases).
             */
            var changed: Boolean
            do {
                changed = false

                val singletons = candidates.values
                    .mapNotNull { it.singleOrNull() }
                    .toSet()

                val ambiguous = candidates.values
                    .filter { it.size != 1 }

                for (wires in ambiguous) {
                    changed = changed or wires.removeAll(singletons)
                }
            } while (changed)

            /*
             * At this point we should now have a segment to candidate wire
             * mapping like:
             *
             * {a=[d], b=[e], c=[a], d=[f], e=[g], f=[b], g=[c]}
             *
             * For convenience later, we invert this (so it goes from
             * segment => wire to wire => segment) and convert the singleton
             * Set<Char>s into Chars.
             *
             * This will result in a final wire to segment mapping like:
             *
             * {a=c, b=f, c=g, d=a, e=b, f=d, g=e}
             */
            val mapping = mutableMapOf<Char, Char>()
            for ((segment, wires) in candidates) {
                val wire = wires.singleOrNull() ?: throw UnsolvableException()
                mapping[wire] = segment
            }

            /*
             * Apply the mapping we've just determined to convert wires to
             * segments, and then convert the segment sets to digits to
             * determine this entry's value.
             */
            var value = 0
            for (wires in output) {
                val segments = wires.map(mapping::get).toSet()
                val digit = DIGITS[segments] ?: throw UnsolvableException()
                value = (value * 10) + digit
            }
            return value
        }

        companion object {
            private val PIPE = Regex(" \\| ")

            fun parse(s: String): Entry {
                val (patterns, output) = s.split(PIPE, limit = 2)
                return Entry(toSets(patterns), toSets(output))
            }

            private fun toSets(s: String): List<Set<Char>> {
                return s.splitToSequence(' ')
                    .map(String::toSet)
                    .toList()
            }
        }
    }

    override fun parse(input: Sequence<String>): List<Entry> {
        return input.map(Entry::parse).toList()
    }

    override fun solvePart1(input: List<Entry>): Int {
        return input.sumOf { entry ->
            entry.output.count { value ->
                value.size == 2 || value.size == 3 || value.size == 4 || value.size == 7
            }
        }
    }

    override fun solvePart2(input: List<Entry>): Int {
        return input.sumOf(Entry::solve)
    }
}
