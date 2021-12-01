package days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day1Test {
    private val input =
        """199
           200
           208
           210
           200
           207
           240
           269
           260
           263"""

    @Test
    fun `number of times a depth measurement increases is correct`() =
        assertEquals(7, solve1_1(input))

    @Test
    fun `number of times a sliding window depth measurement increases is correct`() =
        assertEquals(5, solve1_2(input))
}