package day2

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day2Test {
    private val input =
        """forward 5
           down 5
           forward 8
           up 3
           down 8
           forward 2"""

    @Test
    fun `horizontal position times depth is correct`() =
        Assertions.assertEquals(150, solve2_1(input))

    @Test
    fun `horizontal position times depth with slightly more complicated process is correct`() =
        Assertions.assertEquals(900, solve2_2(input))
}