package day25

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class Day25Test {
    private val input = """v...>>.vv>
                           .vv>>.vv..
                           >>.>v>...v
                           >>v>>.>.v.
                           v>v.vv.v..
                           >.>>..v...
                           .vv..>.>v.
                           v.v..>>v.v
                           ....v..v.>"""

    @Test
    fun `first step in which no sea cucumbers move is correct`() =
        assertEquals(58, solve25_1(input))
}