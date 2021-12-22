package day22

import Coordinate3
import trimmedLines
import kotlin.math.max
import kotlin.math.min

fun solve22_1(input: String) = input.trimmedLines
    .map(RebootStep::fromString)
    .runSteps()
    .map { it.trimTo(-50, 50) }
    .filter(Cuboid::isNotEmpty)
    .sumOf(Cuboid::volume)

fun solve22_2(input: String) = input.trimmedLines
    .map(RebootStep::fromString)
    .runSteps()
    .sumOf(Cuboid::volume)

private data class RebootStep(val turnsCuboidOn: Boolean, val cuboid: Cuboid) {
    companion object {
        val REBOOT_STEP_REGEX =
            Regex("^(on|off) x=(-?\\d+)\\.\\.(-?\\d+),y=(-?\\d+)\\.\\.(-?\\d+),z=(-?\\d+)\\.\\.(-?\\d+)")

        fun fromString(s: String) = REBOOT_STEP_REGEX.find(s)?.groupValues?.let {
            RebootStep(
                it[1] == "on",
                Cuboid(
                    Coordinate3(it[2].toInt(), it[4].toInt(), it[6].toInt()),
                    Coordinate3(it[3].toInt(), it[5].toInt(), it[7].toInt())
                )
            )
        } ?: throw IllegalArgumentException("invalid reboot step syntax")
    }
}

private fun List<RebootStep>.runSteps() = fold(emptyList<Cuboid>()) { on, step ->
    val onMinusStep = on.flatMap { it.subtract(step.cuboid) }
    if (step.turnsCuboidOn) onMinusStep + step.cuboid else onMinusStep
}

data class Cuboid(val minCoordinate: Coordinate3, val maxCoordinate: Coordinate3) {
    val isNotEmpty get() =
        maxCoordinate.x >= minCoordinate.x && maxCoordinate.y >= minCoordinate.y && maxCoordinate.z >= minCoordinate.z

    val volume get() = (maxCoordinate.x - minCoordinate.x + 1).toLong() *
            (maxCoordinate.y - minCoordinate.y + 1) *
            (maxCoordinate.z - minCoordinate.z + 1)

    fun trimTo(trimLow: Int, trimHigh: Int) = Cuboid(
        Coordinate3(max(minCoordinate.x, trimLow), max(minCoordinate.y, trimLow), max(minCoordinate.z, trimLow)),
        Coordinate3(min(maxCoordinate.x, trimHigh), min(maxCoordinate.y, trimHigh), min(maxCoordinate.z, trimHigh))
    )

    fun subtract(other: Cuboid) = when {
        minCoordinate.x > other.maxCoordinate.x || maxCoordinate.x < other.minCoordinate.x ||
                minCoordinate.y > other.maxCoordinate.y || maxCoordinate.y < other.minCoordinate.y ||
                minCoordinate.z > other.maxCoordinate.z || maxCoordinate.z < other.minCoordinate.z -> listOf(this)

        minCoordinate.x >= other.minCoordinate.x && maxCoordinate.x <= other.maxCoordinate.x &&
                minCoordinate.y >= other.minCoordinate.y && maxCoordinate.y <= other.maxCoordinate.y &&
                minCoordinate.z >= other.minCoordinate.z && maxCoordinate.z <= other.maxCoordinate.z -> emptyList()

        else -> listOf(
            // corners
            Cuboid(
                Coordinate3(minCoordinate.x, minCoordinate.y, minCoordinate.z),
                Coordinate3(other.minCoordinate.x - 1, other.minCoordinate.y - 1, other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, other.maxCoordinate.y + 1, other.maxCoordinate.z + 1),
                Coordinate3(maxCoordinate.x, maxCoordinate.y, maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(minCoordinate.x, other.maxCoordinate.y + 1, minCoordinate.z),
                Coordinate3(other.minCoordinate.x - 1, maxCoordinate.y, other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(minCoordinate.x, minCoordinate.y, other.maxCoordinate.z + 1),
                Coordinate3(other.minCoordinate.x - 1, other.minCoordinate.y - 1, maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, minCoordinate.y, minCoordinate.z),
                Coordinate3(maxCoordinate.x, other.minCoordinate.y - 1, other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(minCoordinate.x, other.maxCoordinate.y + 1, other.maxCoordinate.z + 1),
                Coordinate3(other.minCoordinate.x - 1, maxCoordinate.y, maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, minCoordinate.y, other.maxCoordinate.z + 1),
                Coordinate3(maxCoordinate.x, other.minCoordinate.y - 1, maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, other.maxCoordinate.y + 1, minCoordinate.z),
                Coordinate3(maxCoordinate.x, maxCoordinate.y, other.minCoordinate.z - 1)
            ),

            // edges x
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), minCoordinate.y, minCoordinate.z),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), other.minCoordinate.y - 1, other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), other.maxCoordinate.y + 1, minCoordinate.z),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), maxCoordinate.y, other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), other.maxCoordinate.y + 1, other.maxCoordinate.z + 1),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), maxCoordinate.y, maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), minCoordinate.y, other.maxCoordinate.z + 1),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), other.minCoordinate.y - 1, maxCoordinate.z)
            ),

            // edges y
            Cuboid(
                Coordinate3(minCoordinate.x, max(minCoordinate.y, other.minCoordinate.y), minCoordinate.z),
                Coordinate3(other.minCoordinate.x - 1, min(maxCoordinate.y, other.maxCoordinate.y), other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, max(minCoordinate.y, other.minCoordinate.y), minCoordinate.z),
                Coordinate3(maxCoordinate.x, min(maxCoordinate.y, other.maxCoordinate.y), other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, max(minCoordinate.y, other.minCoordinate.y), other.maxCoordinate.z + 1),
                Coordinate3(maxCoordinate.x, min(maxCoordinate.y, other.maxCoordinate.y), maxCoordinate.z)
            ),
            Cuboid(
                Coordinate3(minCoordinate.x, max(minCoordinate.y, other.minCoordinate.y), other.maxCoordinate.z + 1),
                Coordinate3(other.minCoordinate.x - 1, min(maxCoordinate.y, other.maxCoordinate.y), maxCoordinate.z)
            ),

            // edges z
            Cuboid(
                Coordinate3(minCoordinate.x, minCoordinate.y, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(other.minCoordinate.x - 1, other.minCoordinate.y - 1, min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, minCoordinate.y, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(maxCoordinate.x, other.minCoordinate.y - 1, min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, other.maxCoordinate.y + 1, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(maxCoordinate.x, maxCoordinate.y, min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(minCoordinate.x, other.maxCoordinate.y + 1, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(other.minCoordinate.x - 1, maxCoordinate.y, min(maxCoordinate.z, other.maxCoordinate.z))
            ),

            // sides
            Cuboid(
                Coordinate3(minCoordinate.x, max(minCoordinate.y, other.minCoordinate.y), max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(other.minCoordinate.x - 1, min(maxCoordinate.y, other.maxCoordinate.y), min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(other.maxCoordinate.x + 1, max(minCoordinate.y, other.minCoordinate.y), max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(maxCoordinate.x, min(maxCoordinate.y, other.maxCoordinate.y), min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), minCoordinate.y, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), other.minCoordinate.y - 1, min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), other.maxCoordinate.y + 1, max(minCoordinate.z, other.minCoordinate.z)),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), maxCoordinate.y, min(maxCoordinate.z, other.maxCoordinate.z))
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), max(minCoordinate.y, other.minCoordinate.y), minCoordinate.z),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), min(maxCoordinate.y, other.maxCoordinate.y), other.minCoordinate.z - 1)
            ),
            Cuboid(
                Coordinate3(max(minCoordinate.x, other.minCoordinate.x), max(minCoordinate.y, other.minCoordinate.y), other.maxCoordinate.z + 1),
                Coordinate3(min(maxCoordinate.x, other.maxCoordinate.x), min(maxCoordinate.y, other.maxCoordinate.y), maxCoordinate.z)
            ),
        ).filter(Cuboid::isNotEmpty)
    }
}