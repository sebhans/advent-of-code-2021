package days

import trimmedLines
import kotlin.math.max
import kotlin.math.min

fun solve5_1(input: String) = input.trimmedLines
    .map(Line::fromString)
    .filter(Line::isHorizontalOrVertical)
    .flatMap(Line::toPoints)
    .groupBy { it }
    .filterValues { it.size > 1 }
    .size

private data class LinePoint(val x: Int, val y: Int) {
    companion object {
        fun fromString(s: String) = s.split(',').let { LinePoint(it[0].toInt(), it[1].toInt()) }
    }
}

private data class Line(val start: LinePoint, val end: LinePoint) {
    companion object {
        fun fromString(s: String) =
            s.split(" -> ").let { Line(LinePoint.fromString(it[0]), LinePoint.fromString(it[1])) }
    }

    fun isHorizontal() = start.y == end.y

    fun isVertical() = start.x == end.x

    fun isHorizontalOrVertical() = isHorizontal() || isVertical()

    fun toPoints() = when {
        isHorizontal() -> (min(start.x, end.x)..max(start.x, end.x)).map { LinePoint(it, start.y) }
        isVertical() -> (min(start.y, end.y)..max(start.y, end.y)).map { LinePoint(start.x, it) }
        else -> throw NotImplementedError()
    }
}