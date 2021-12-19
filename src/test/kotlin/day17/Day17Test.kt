package day17

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day17Test {
    private val input = "target area: x=20..30, y=-10..-5"

    @Test
    fun `TargetArea is parsed correctly`() =
        assertEquals(TargetArea(20, 30, -10, -5), TargetArea.fromString(input))

    @Test
    fun `min initial x is correct`() =
        assertEquals(6, TargetArea.fromString(input).minInitialDX)

    @Test
    fun `highest reachable y position is correct`() =
        assertEquals(45, solve17_1(input))

    @Test
    fun `number of distinct hitting initial velocities is correct`() =
        assertEquals(112, solve17_2(input))
}