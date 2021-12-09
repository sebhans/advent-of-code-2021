package days

import asDigitList
import fixedPoint
import trimmedLines

fun solve9_1(input: String) = input.trimmedLines
    .map(String::asDigitList)
    .let { heightMap ->
        heightMap.lowPoints.fold(0) { n, p -> n + heightMap.at(p) + 1 }
    }

fun solve9_2(input: String) = input.trimmedLines
    .map(String::asDigitList)
    .let { heightMap ->
        heightMap.lowPoints
            .map(heightMap::basinAround)
            .map(Basin::size)
            .sortedDescending()
            .take(3)
            .reduce { a, b -> a * b}
    }

private typealias HeightMap = List<List<Int>>
private typealias Basin = Set<Coordinate>

private val HeightMap.lowPoints
    get() =
        (0..lastIndex).flatMap { y ->
            val line = this[y]
            (0..line.lastIndex)
                .filter { x -> neighboursOf(x, y).all { at(it) > line[x] } }
                .map { x -> Coordinate(x, y) }
        }

private data class Coordinate(val x: Int, val y: Int)

private fun HeightMap.at(p: Coordinate) = this[p.y][p.x]

private fun HeightMap.neighboursOf(x: Int, y: Int): Set<Coordinate> {
    val m = this
    return buildSet {
        if (x > 0) add(Coordinate(x - 1, y))
        if (x < m[y].lastIndex) add(Coordinate(x + 1, y))
        if (y > 0) add(Coordinate(x, y - 1))
        if (y < m.lastIndex) add(Coordinate(x, y + 1))
    }
}

private fun HeightMap.neighboursOf(p: Coordinate) = neighboursOf(p.x, p.y)

private fun HeightMap.neighboursOf(basin: Basin) = basin.flatMap { neighboursOf(it) }.toSet()

private fun HeightMap.expandBasin(basin: Basin) = basin union neighboursOf(basin).filter(notHighPoint()).toSet()

private fun HeightMap.notHighPoint() = { p: Coordinate -> this.at(p) != 9 }

private fun HeightMap.basinAround(p: Coordinate) = fixedPoint(setOf(p), this::expandBasin)