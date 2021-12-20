package day18

import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.test.assertEquals

internal class Day18Test {
    private val input="""[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
                         [[[5,[2,8]],4],[5,[[9,9],0]]]
                         [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
                         [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
                         [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
                         [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
                         [[[[5,4],[7,7]],8],[[8,3],8]]
                         [[9,3],[[9,9],[6,[4,9]]]]
                         [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
                         [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"""

    @Test
    fun `snailfish number is parsed correctly`() =
        assertEquals(
            SnailfishNumber(
                SnailfishPair(
                    SnailfishPair(
                        SnailfishPair(
                            SnailfishPair(SnailfishLeaf(1), SnailfishLeaf(2)),
                            SnailfishPair(SnailfishLeaf(3), SnailfishLeaf(4))
                        ),
                        SnailfishPair(
                            SnailfishPair(SnailfishLeaf(5), SnailfishLeaf(6)),
                            SnailfishPair(SnailfishLeaf(7), SnailfishLeaf(8))
                        ),
                    ),
                    SnailfishLeaf(9)
                ),
            ),
            SnailfishNumber.fromString("[[[[1,2],[3,4]],[[5,6],[7,8]]],9]")
        )

    @ParameterizedTest
    @CsvSource(
        "[9,1];29",
        "[[9,1],[1,9]];129",
        "[[1,2],[[3,4],5]];143",
        "[[[[0,7],4],[[7,8],[6,0]]],[8,1]];1384",
        "[[[[1,1],[2,2]],[3,3]],[4,4]];445",
        "[[[[3,0],[5,3]],[4,4]],[5,5]];791",
        "[[[[5,0],[7,4]],[5,5]],[6,6]];1137",
        "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]];3488",
        "[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]];4140",
        delimiter = ';'
    )
    fun `magnitude is correct`(snailfishNumber: String, expectedMagnitude: Int) =
        assertEquals(expectedMagnitude, SnailfishNumber.fromString(snailfishNumber).magnitude)

    @ParameterizedTest
    @CsvSource(
        "[[[[[9,8],1],2],3],4];[[[[0,9],2],3],4]",
        "[7,[6,[5,[4,[3,2]]]]];[7,[6,[5,[7,0]]]]",
        "[[6,[5,[4,[3,2]]]],1];[[6,[5,[7,0]]],3]",
        "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]];[[3,[2,[8,0]]],[9,[5,[7,0]]]]",
        "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]];[[3,[2,[8,0]]],[9,[5,[7,0]]]]",
        delimiter = ';'
    )
    fun `reduce explodes correctly`(snailfishNumber: String, expectedExplodedSnailfishNumber: String) =
        assertEquals(
            SnailfishNumber.fromString(expectedExplodedSnailfishNumber),
            SnailfishNumber.fromString(snailfishNumber).reduce()
        )

    @Test
    fun `reduce splits even number correctly`() =
        assertEquals(
            SnailfishNumber(SnailfishPair(SnailfishPair(SnailfishLeaf(5), SnailfishLeaf(5)), SnailfishLeaf(1))),
            SnailfishNumber(SnailfishPair(SnailfishLeaf(10), SnailfishLeaf(1))).reduce()
        )

    @Test
    fun `reduce splits odd number correctly`() =
        assertEquals(
            SnailfishNumber(SnailfishPair(SnailfishLeaf(1), SnailfishPair(SnailfishLeaf(5), SnailfishLeaf(6)))),
            SnailfishNumber(SnailfishPair(SnailfishLeaf(1), SnailfishLeaf(11))).reduce()
        )

    @Test
    fun `reduce splits nested number correctly`() =
        assertEquals(
            SnailfishNumber(
                SnailfishPair(
                    SnailfishPair(
                        SnailfishLeaf(2),
                        SnailfishPair(SnailfishLeaf(1), SnailfishPair(SnailfishLeaf(5), SnailfishLeaf(6)))
                    ), SnailfishLeaf(3)
                )
            ),
            SnailfishNumber(
                SnailfishPair(
                    SnailfishPair(
                        SnailfishLeaf(2),
                        SnailfishPair(SnailfishLeaf(1), SnailfishLeaf(11))
                    ), SnailfishLeaf(3)
                )
            ).reduce()
        )

    @ParameterizedTest
    @CsvSource(
        "[1,2];[[3,4],5];[[1,2],[[3,4],5]]",
        "[[[[4,3],4],4],[7,[[8,4],9]]];[1,1];[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
        delimiter = ';'
    )
    fun `add adds and reduces correctly`(a: String, b: String, expectedSum: String) =
        assertEquals(
            SnailfishNumber.fromString(expectedSum),
            SnailfishNumber.fromString(a).add(SnailfishNumber.fromString(b))
        )

    @Test
    fun `adds lists correctly`() =
        assertEquals(4140, solve18_1(input))

    @Test
    fun `largest magnitude of sum of any two snailfish number is correct`() =
        assertEquals(3993, solve18_2(input))
}