package day5

import trimmedLines
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

fun solve5_1(input: String) = input.trimmedLines
    .map(HydrothermalVentCloudLine::fromString)
    .filter(HydrothermalVentCloudLine::isHorizontalOrVertical)
    .countPointsOfOverlap()

fun solve5_2(input: String) = input.trimmedLines
    .map(HydrothermalVentCloudLine::fromString)
    .countPointsOfOverlap()

data class HydrothermalVentCloudLinePoint(val x: Int, val y: Int) {
    companion object {
        fun fromString(s: String) = s.split(',').let { HydrothermalVentCloudLinePoint(it[0].toInt(), it[1].toInt()) }
    }
}

data class HydrothermalVentCloudLine(
    val start: HydrothermalVentCloudLinePoint,
    val end: HydrothermalVentCloudLinePoint
) {
    companion object {
        fun fromString(s: String) =
            s.split(" -> ").let {
                HydrothermalVentCloudLine(
                    HydrothermalVentCloudLinePoint.fromString(it[0]),
                    HydrothermalVentCloudLinePoint.fromString(it[1])
                )
            }
    }

    fun isHorizontal() = start.y == end.y

    fun isVertical() = start.x == end.x

    fun isHorizontalOrVertical() = isHorizontal() || isVertical()

    fun isRisingDiagonal() = (start.x - end.x).sign == (start.y - end.y).sign

    fun toPoints() = when {
        isHorizontal() -> (min(start.x, end.x)..max(start.x, end.x)).map { HydrothermalVentCloudLinePoint(it, start.y) }
        isVertical() -> (min(start.y, end.y)..max(start.y, end.y)).map { HydrothermalVentCloudLinePoint(start.x, it) }
        isRisingDiagonal() -> (min(start.x, end.x)..max(start.x, end.x)).map { HydrothermalVentCloudLinePoint(it, start.y + it - start.x) }
        else -> (min(start.x, end.x)..max(start.x, end.x)).map { HydrothermalVentCloudLinePoint(it, start.y - it + start.x) }
    }
}

private fun Iterable<HydrothermalVentCloudLine>.countPointsOfOverlap() =
    flatMap(HydrothermalVentCloudLine::toPoints)
        .groupBy { it }
        .filterValues { it.size > 1 }
        .size
