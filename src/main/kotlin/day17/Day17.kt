package day17

import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sqrt

fun solve17_1(input: String) = TargetArea.fromString(input).highestYPositionStillHitting

fun solve17_2(input: String) = TargetArea.fromString(input).let {
    var hits = 0
    for (dx in it.minInitialDX..it.maxInitialDX) {
        for (dy in it.minInitialDY..it.maxInitialDY) {
            if (it.shootAt(dx, dy)) {
                hits++
            }
        }
    }
    hits
}

data class TargetArea(val minX: Int, val maxX: Int, val minY: Int, val maxY: Int) {
    companion object {
        private val TARGET_AREA_REGEX = Regex("target area: x=(-?\\d+)\\.\\.(-?\\d+), y=(-?\\d+)\\.\\.(-?\\d+)")
        fun fromString(s: String) = TARGET_AREA_REGEX
            .find(s)
            ?.groupValues
            ?.let { TargetArea(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt()) }
            ?: throw IllegalArgumentException("cannot parse target area")
    }

    val minInitialDX get() = ceil(sqrt(abs(0.5 - 2 * minX)) - 0.5).toInt()

    val maxInitialDX get() = maxX

    val minInitialDY get() = minY

    val maxInitialDY get() = abs(minY) - 1

    val highestYPositionStillHitting get() = maxInitialDY * (maxInitialDY + 1) / 2

    fun shootAt(initialDX: Int, initialDY: Int): Boolean {
        var x = 0
        var y = 0
        var dx = initialDX
        var dy = initialDY
        while (x < minX || y > maxY) {
            if (x > maxX || y < minY) {
                return false
            }
            x += dx
            y += dy
            dx = when {
                dx > 0 -> dx - 1
                dx < 0 -> dx + 1
                else -> dx
            }
            dy--
        }
        return x <= maxX && y >= minY
    }
}