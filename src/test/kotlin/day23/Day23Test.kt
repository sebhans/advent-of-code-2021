package day23

import Coordinate3
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class Day23Test {
    private val input = """#############
                          |#...........#
                          |###B#C#B#D###
                          |  #A#D#C#A#
                          |  #########""".trimMargin()

    @Test
    fun `minimum energy for 8 amphipods is correct`() =
        assertEquals(12521, solve23_1(input))

    @Test
    fun `minimum energy for 16 amphipods is correct`() =
        assertEquals(44169, solve23_2(input))
}