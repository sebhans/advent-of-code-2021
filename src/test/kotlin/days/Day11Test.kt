package days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day11Test {
    private val input = """5483143223
                           2745854711
                           5264556173
                           6141336146
                           6357385478
                           4167524645
                           2176841721
                           6882881134
                           4846848554
                           5283751526"""

    @Test
    fun `number of flashes after 100 steps is correct`() =
        assertEquals(1656, solve11_1(input))

    @Test
    fun `first step during which all octopuses flash is correct`() =
        assertEquals(195, solve11_2(input))
}