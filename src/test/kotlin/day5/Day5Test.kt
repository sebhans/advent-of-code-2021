package day5

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

internal class Day5Test {
    private val input =
        """0,9 -> 5,9
           8,0 -> 0,8
           9,4 -> 3,4
           2,2 -> 2,1
           7,0 -> 7,4
           6,4 -> 2,0
           0,9 -> 2,9
           3,4 -> 1,4
           0,0 -> 8,8
           5,5 -> 8,2"""

    @Test
    fun `number of points where at least two horizontal and vertical lines overlap is correct`() =
        Assertions.assertEquals(5, solve5_1(input))

    @Test
    fun `toPoints works for horizontal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(2, 1),
                HydrothermalVentCloudLinePoint(3, 1)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(3, 1)
            ).toPoints()
        )

    @Test
    fun `toPoints works for reverse horizontal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(2, 1),
                HydrothermalVentCloudLinePoint(3, 1)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(3, 1),
                HydrothermalVentCloudLinePoint(1, 1)
            ).toPoints()
        )

    @Test
    fun `toPoints works for vertical line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(1, 2),
                HydrothermalVentCloudLinePoint(1, 3)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(1, 3)
            ).toPoints()
        )

    @Test
    fun `toPoints works for reverse vertical line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(1, 2),
                HydrothermalVentCloudLinePoint(1, 3)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(1, 3),
                HydrothermalVentCloudLinePoint(1, 1)
            ).toPoints()
        )

    @Test
    fun `toPoints works for rising diagonal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(2, 2),
                HydrothermalVentCloudLinePoint(3, 3)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(3, 3)
            ).toPoints()
        )

    @Test
    fun `toPoints works for reverse rising diagonal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 1),
                HydrothermalVentCloudLinePoint(2, 2),
                HydrothermalVentCloudLinePoint(3, 3)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(3, 3),
                HydrothermalVentCloudLinePoint(1, 1)
            ).toPoints()
        )

    @Test
    fun `toPoints works for falling diagonal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 3),
                HydrothermalVentCloudLinePoint(2, 2),
                HydrothermalVentCloudLinePoint(3, 1)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(1, 3),
                HydrothermalVentCloudLinePoint(3, 1)
            ).toPoints()
        )

    @Test
    fun `toPoints works for reverse falling diagonal line`() =
        assertContentEquals(
            listOf(
                HydrothermalVentCloudLinePoint(1, 3),
                HydrothermalVentCloudLinePoint(2, 2),
                HydrothermalVentCloudLinePoint(3, 1)
            ),
            HydrothermalVentCloudLine(
                HydrothermalVentCloudLinePoint(3, 1),
                HydrothermalVentCloudLinePoint(1, 3)
            ).toPoints()
        )

    @Test
    fun `number of points where at least two lines overlap is correct`() =
        Assertions.assertEquals(12, solve5_2(input))
}