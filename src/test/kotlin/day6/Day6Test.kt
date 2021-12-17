package day6

import asCommaSeparatedInts
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day6Test {
    private val input = "3,4,3,1,2"

    @Test
    fun `number of lanternfishes after 18 days is correct`() =
        Assertions.assertEquals(26, LanternfishPopulation.of(input.asCommaSeparatedInts).passDays(18).size)

    @Test
    fun `number of lanternfishes after 80 days is correct`() =
        Assertions.assertEquals(5934, solve6_1(input))

    @Test
    fun `number of lanternfishes after 256 days is correct`() =
        Assertions.assertEquals(26984457539, solve6_2(input))
}