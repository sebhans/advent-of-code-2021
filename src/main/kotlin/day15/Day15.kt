package day15

import Coordinate
import asDigitMatrix
import java.util.*

fun solve15_1(input: String) = input.asDigitMatrix.toArrayMap(1).findLowRiskPathTotal()

fun solve15_2(input: String) = input.asDigitMatrix.toArrayMap(5).findLowRiskPathTotal()

private fun List<List<Int>>.toArrayMap(factor: Int) =
    Array(size * factor) { y ->
        Array(this[0].size * factor) { x ->
            this[y % size][x % this[0].size] plusRisk (y / size + x / this[0].size)
        }
    }

private infix fun Int.plusRisk(n: Int) = ((this - 1) + n) % 9 + 1

private fun Array<Array<Int>>.findLowRiskPathTotal(): Long {
    val paths = PriorityQueue(listOf(Path()))
    val height = this.size
    val width = this[0].size
    val end = Coordinate(width - 1, height - 1)
    val minRisks = mutableMapOf<Coordinate, Long>()
    while (!paths.peek().contains(end)) {
        val path = paths.poll()
        fun step(dx: Int, dy: Int) = Coordinate(path.x + dx, path.y + dy).also {
            if (!path.contains(it)) {
                val stepRisk = this[it.y][it.x]
                val newPathRisk = path.risk + stepRisk
                val minPositionRisk = minRisks[it]
                if (minPositionRisk == null || newPathRisk < minPositionRisk) {
                    minRisks[it] = newPathRisk
                    paths.add(path.step(it, stepRisk))
                }
            }
        }
        if (path.x > 0) step(-1, 0)
        if (path.x < width - 1) step(+1, 0)
        if (path.y > 0) step(0, -1)
        if (path.y < height - 1) step(0, +1)
    }
    return paths.peek().risk
}

private data class Path(val visited: Set<Coordinate>, val position: Coordinate, val risk: Long) : Comparable<Path> {
    constructor() : this(setOf(Coordinate(0, 0)), Coordinate(0, 0), 0)

    val x get() = position.x
    val y get() = position.y

    fun contains(c: Coordinate) = visited.contains(c)

    fun step(where: Coordinate, risk: Int) = Path(visited + where, where, this.risk + risk)

    override fun compareTo(other: Path): Int =
        risk.compareTo(other.risk)
            .let { if (it != 0) it else (x + y).compareTo(other.x + other.y) }
}