package day9

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day9Test {
    private val input = """2199943210
                           3987894921
                           9856789892
                           8767896789
                           9899965678"""

    @Test
    fun `sum of risk levels of all low points on the heightmap is correct`() =
        Assertions.assertEquals(15, solve9_1(input))

    @Test
    fun `product of three largest basin sizes is correct`() =
        Assertions.assertEquals(1134, solve9_2(input))
}