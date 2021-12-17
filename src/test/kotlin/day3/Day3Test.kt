package day3

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day3Test {
    private val input =
        """00100
           11110
           10110
           10111
           10101
           01111
           00111
           11100
           10000
           11001
           00010
           01010"""

    @Test
    fun `power consumption is correct`() =
        Assertions.assertEquals(198, solve3_1(input))

    @Test
    fun `life support rating is correct`() =
        Assertions.assertEquals(230, solve3_2(input))
}