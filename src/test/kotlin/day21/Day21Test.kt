package day21

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day21Test {
    private val input = """Player 1 starting position: 4
                           Player 2 starting position: 8"""

    @Test
    fun `score of losing player times die rools is correct`() =
        assertEquals(739785, solve21_1(input))

    @Test
    fun `number of universes of winning player is correct`() =
        assertEquals(444356092776315, solve21_2(input))
}