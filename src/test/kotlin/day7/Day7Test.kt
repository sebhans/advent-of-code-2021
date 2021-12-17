package day7

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day7Test {
    private val input = "16,1,2,0,4,2,7,1,2,14"

    @Test
    fun `fuel cost is correct`() =
        Assertions.assertEquals(37, solve7_1(input))

    @Test
    fun `fuel cost with better understanding is correct`() =
        Assertions.assertEquals(168, solve7_2(input))
}