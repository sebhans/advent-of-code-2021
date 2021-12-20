package day19

import asCommaSeparatedInts
import first
import rest
import splitByEmptyLines
import trimmedLines
import kotlin.math.abs
import kotlin.math.max

fun solve19_1(input: String) = input.splitByEmptyLines
    .map(Scanner::fromString)
    .align()
    .map(Scanner::detectedBeacons)
    .reduce(Set<Beacon>::union)
    .size

fun solve19_2(input: String) = input.splitByEmptyLines
    .map(Scanner::fromString)
    .align()
    .let {
        var largestManhattanDistance = 0
        for (i in 0..it.lastIndex - 1) {
            for (j in (i + 1)..it.lastIndex) {
                largestManhattanDistance = max(largestManhattanDistance, it[i].offset.manhattanDistanceTo(it[j].offset))
            }
        }
        largestManhattanDistance
    }

fun List<Scanner>.align(): List<Scanner> {
    val completedScanners = mutableListOf<Scanner>()
    val alignedScanners = mutableListOf(first)
    val unalignedScanners = rest.toMutableList()
    while (alignedScanners.isNotEmpty() && unalignedScanners.isNotEmpty()) {
        val aligned = alignedScanners.removeAt(0)
        for (unaligned in unalignedScanners) {
            unaligned.alignWith(aligned)?.also(alignedScanners::add)
        }
        unalignedScanners.removeAll(alignedScanners)
        completedScanners.add(aligned)
    }
    completedScanners.addAll(alignedScanners)
    return completedScanners
}

class Scanner(val number: Int, val detectedBeacons: Set<Beacon>, val offset: Offset) {
    companion object {
        fun fromString(s: String) = s.trimmedLines.let {
            Scanner(
                Regex("\\d+").find(it.first)?.value?.toInt()
                    ?: throw IllegalArgumentException("malformed scanner header"),
                it.rest.map(Beacon::fromString).toSet()
            )
        }
    }

    constructor(number: Int, detectedBeacons: Set<Beacon>) : this(number, detectedBeacons, Offset(0, 0, 0))

    fun alignWith(other: Scanner, confidence: Int = 12) = ORIENTATIONS
        .map { it to offsetFrom(other, it, confidence) }
        .firstOrNull { it.second != null }
        ?.let { toOrientation(it.first).withOffset(it.second!!) }

    private fun offsetFrom(other: Scanner, orientation: BeaconTransformation, confidence: Int) =
        toOrientation(orientation).offsetFrom(other, confidence)

    fun offsetFrom(other: Scanner, confidence: Int) = detectedBeacons
        .flatMap { myBeacon -> other.detectedBeacons.map { otherBeacon -> Offset.from(myBeacon, otherBeacon) } }
        .find { (detectedBeacons.withOffset(it) intersect other.detectedBeacons).size >= confidence }

    private fun toOrientation(orientation: BeaconTransformation) =
        Scanner(number, detectedBeacons.map(orientation).toSet(), offset)

    private fun withOffset(offset: Offset) = Scanner(number, detectedBeacons.withOffset(offset), offset)

    override fun equals(other: Any?) = other is Scanner && number == other.number

    override fun hashCode() = number
}

typealias BeaconTransformation = (Beacon) -> Beacon

private val FACINGS = listOf<BeaconTransformation>(
    { beacon -> Beacon(beacon.x, beacon.y, beacon.z) },
    { beacon -> Beacon(-beacon.x, -beacon.y, beacon.z) },
    { beacon -> Beacon(beacon.y, -beacon.x, beacon.z) },
    { beacon -> Beacon(-beacon.y, beacon.x, beacon.z) },
    { beacon -> Beacon(beacon.z, beacon.y, -beacon.x) },
    { beacon -> Beacon(-beacon.z, beacon.y, beacon.x) },
)

private val ROTATIONS = listOf<BeaconTransformation>(
    { beacon -> Beacon(beacon.x, beacon.y, beacon.z) },
    { beacon -> Beacon(beacon.x, -beacon.z, beacon.y) },
    { beacon -> Beacon(beacon.x, beacon.z, -beacon.y) },
    { beacon -> Beacon(beacon.x, -beacon.y, -beacon.z) },
)

private val ORIENTATIONS = FACINGS.flatMap { f -> ROTATIONS.map { r -> { beacon: Beacon -> f(r(beacon)) } } }

data class Beacon(val x: Int, val y: Int, val z: Int) {
    companion object {
        fun fromString(s: String) = s.asCommaSeparatedInts.let { Beacon(it[0], it[1], it[2]) }
    }

    fun withOffset(offset: Offset) = Beacon(x + offset.dx, y + offset.dy, z + offset.dz)
}

fun Set<Beacon>.withOffset(offset: Offset) = map { it.withOffset(offset) }.toSet()

data class Offset(val dx: Int, val dy: Int, val dz: Int) {
    companion object {
        fun from(start: Beacon, end: Beacon) = Offset(end.x - start.x, end.y - start.y, end.z - start.z)
    }

    fun manhattanDistanceTo(other: Offset) = abs(dx - other.dx) + abs(dy - other.dy) + abs(dz - other.dz)
}