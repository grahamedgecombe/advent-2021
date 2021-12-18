package com.grahamedgecombe.advent2021.day18

import com.grahamedgecombe.advent2021.day18.Day18.Cell
import com.grahamedgecombe.advent2021.day18.Day18.Cell.Atom
import kotlin.test.Test
import kotlin.test.assertEquals

object Day18Test {
    private val TEST_INPUT_1 = Day18.parse("""
        [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
        [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
        [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
        [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
        [7,[5,[[3,8],[1,4]]]]
        [[2,[2,2]],[8,[8,1]]]
        [2,9]
        [1,[[[9,3],9],[[9,0],[0,7]]]]
        [[[5,[7,4]],7],1]
        [[[[4,2],2],6],[8,7]]
    """.trimIndent())
    private val TEST_INPUT_2 = Day18.parse("""
        [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
        [[[5,[2,8]],4],[5,[[9,9],0]]]
        [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
        [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
        [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
        [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
        [[[[5,4],[7,7]],8],[[8,3],8]]
        [[9,3],[[9,9],[6,[4,9]]]]
        [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
        [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
    """.trimIndent())
    private val PROD_INPUT = Day18.parse()

    @Test
    fun testPart1() {
        // explode
        assertEquals(Cell.parse("[[[[0,9],2],3],4]"), Cell.parse("[[[[[9,8],1],2],3],4]").explode())
        assertEquals(Cell.parse("[7,[6,[5,[7,0]]]]"), Cell.parse("[7,[6,[5,[4,[3,2]]]]]").explode())
        assertEquals(Cell.parse("[[6,[5,[7,0]]],3]"), Cell.parse("[[6,[5,[4,[3,2]]]],1]").explode())
        assertEquals(
            Cell.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]"),
            Cell.parse("[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]").explode()
        )
        assertEquals(
            Cell.parse("[[3,[2,[8,0]]],[9,[5,[7,0]]]]"),
            Cell.parse("[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]").explode()
        )

        // split
        assertEquals(Cell.parse("[5,5]"), Atom(10).split())
        assertEquals(Cell.parse("[5,6]"), Atom(11).split())
        assertEquals(Cell.parse("[6,6]"), Atom(12).split())

        // reduce
        assertEquals(
            Cell.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]"),
            Cell.parse("[[[[4,3],4],4],[7,[[8,4],9]]]").plus(Cell.parse("[1,1]")).reduce()
        )

        // sum
        assertEquals(Cell.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]"), Day18.sumOf(listOf(
            Cell.parse("[1,1]"),
            Cell.parse("[2,2]"),
            Cell.parse("[3,3]"),
            Cell.parse("[4,4]"),
        )))

        assertEquals(Cell.parse("[[[[3,0],[5,3]],[4,4]],[5,5]]"), Day18.sumOf(listOf(
            Cell.parse("[1,1]"),
            Cell.parse("[2,2]"),
            Cell.parse("[3,3]"),
            Cell.parse("[4,4]"),
            Cell.parse("[5,5]"),
        )))

        assertEquals(Cell.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]"), Day18.sumOf(listOf(
            Cell.parse("[1,1]"),
            Cell.parse("[2,2]"),
            Cell.parse("[3,3]"),
            Cell.parse("[4,4]"),
            Cell.parse("[5,5]"),
            Cell.parse("[6,6]"),
        )))

        assertEquals(Cell.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]"), Day18.sumOf(TEST_INPUT_1))

        // magnitude
        assertEquals(29, Cell.parse("[9,1]").getMagnitude())
        assertEquals(21, Cell.parse("[1,9]").getMagnitude())
        assertEquals(129, Cell.parse("[[9,1],[1,9]]").getMagnitude())
        assertEquals(143, Cell.parse("[[1,2],[[3,4],5]]").getMagnitude())
        assertEquals(1384, Cell.parse("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").getMagnitude())
        assertEquals(445, Cell.parse("[[[[1,1],[2,2]],[3,3]],[4,4]]").getMagnitude())
        assertEquals(791, Cell.parse("[[[[3,0],[5,3]],[4,4]],[5,5]]").getMagnitude())
        assertEquals(1137, Cell.parse("[[[[5,0],[7,4]],[5,5]],[6,6]]").getMagnitude())
        assertEquals(3488, Cell.parse("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").getMagnitude())

        // entire process
        assertEquals(4140, Day18.solvePart1(TEST_INPUT_2))
        assertEquals(4391, Day18.solvePart1(PROD_INPUT))
    }
}
