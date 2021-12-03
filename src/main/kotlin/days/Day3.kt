package days

import trimmedLines

fun solve3_1(input: String): Int {
    val lines = input.trimmedLines
    val gammaRate =  lines
        .map(::toBinaryDigits)
        .reduce(::addLists)
        .map { if (it > lines.size / 2) 1 else 0 }
        .joinToString(separator = "", transform = Int::toString)
        .toInt(2)
    val epsilonRate = gammaRate xor ((1 shl lines[0].length) - 1)
    return gammaRate * epsilonRate
}

private fun toBinaryDigits(s: String) = s.chunked(1).map { it.toInt(2) }

private fun addLists(a: List<Int>, b: List<Int>) =
    a.zip(b).map() { it.first + it.second }