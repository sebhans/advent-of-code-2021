package days

import first
import rest
import trimmedLines

fun solve1_1(input: String) = input.trimmedLines.map(String::toInt).let {
    it.rest.fold(0 to it.first) {
        acc, depth -> Pair(if (depth > acc.second) acc.first + 1 else acc.first, depth)
    }.first
}

fun solve1_2(input: String): Int {
    val depths = input.trimmedLines.map(String::toInt)
    return depths.subList(0, depths.size - 3)
        .zip(depths.subList(3, depths.size))
        .filter { it.second > it.first }
        .size
}