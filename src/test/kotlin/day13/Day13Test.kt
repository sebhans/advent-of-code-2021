package day13

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day13Test {
    private val input = """6,10
                           0,14
                           9,10
                           0,3
                           10,4
                           4,11
                           6,0
                           6,12
                           4,1
                           0,13
                           10,12
                           3,4
                           3,0
                           8,4
                           1,10
                           2,14
                           8,10
                           9,0

                           fold along y=7
                           fold along x=5"""

    @Test
    fun `number of dots after the first fold instruction is correct`() =
        Assertions.assertEquals(17, solve13_1(input))

    @Test
    fun `code is correct`() =
        Assertions.assertEquals(
            """
                       |
                       |#####
                       |#...#
                       |#...#
                       |#...#
                       |#####
                       |""".trimMargin(), solve13_2(input)
        )
}