package day15

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day15Test {
    private val input = """1163751742
                           1381373672
                           2136511328
                           3694931569
                           7463417111
                           1319128137
                           1359912421
                           3125421639
                           1293138521
                           2311944581"""

    @Test
    fun `lowest total risk is correct`() =
        Assertions.assertEquals(40, solve15_1(input))

    @Test
    fun `lowest total risk for winding path is correct`() =
        Assertions.assertEquals(
            8, solve15_1(
                """119
                         919
                         119
                         199
                         111"""
            )
        )

    @Test
    fun `lowest total risk for extended cavern is correct`() =
        Assertions.assertEquals(315, solve15_2(input))
}