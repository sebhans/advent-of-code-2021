package days

import trimmedLines

fun solve8_1(input: String) = input.trimmedLines
    .flatMap(::toDigits)
    .filter { it in setOf(1, 4, 7, 8) }
    .size

fun solve8_2(input: String) = input.trimmedLines
    .map(::toDigits)
    .sumOf(::outputValue)

private fun toDigits(line: String) = line.split(" | ").let { parts ->
    val digitMap = parts[0].toPatternList().toDigitMap()
    parts[1].toPatternList().map { digitMap[it]!! }
}

private fun String.toPatternList() = split(' ').map(CharSequence::toSortedSet)

private fun List<Set<Char>>.toDigitMap(): Map<Set<Char>, Int> {
    val digitsToPatterns = associate {
        when (it.size) {
            2 -> 1
            4 -> 4
            3 -> 7
            7 -> 8
            else -> -1
        } to it
    }.filterKeys { it != -1 }.toMutableMap()

    val zeroTwoThreeFiveSixNine = filter { it !in digitsToPatterns.values }
    val eg = digitsToPatterns[8]!! subtract digitsToPatterns[7]!! subtract digitsToPatterns[4]!!
    digitsToPatterns[2] = zeroTwoThreeFiveSixNine.first { it.size == 5 && it.containsAll(eg) }

    val zeroThreeFiveSixNine = zeroTwoThreeFiveSixNine.filter { it != digitsToPatterns[2] }
    val bd = digitsToPatterns[4]!! subtract digitsToPatterns[1]!!
    digitsToPatterns[0] = zeroThreeFiveSixNine.first { !it.containsAll(bd) && it.containsAll(eg) }
    digitsToPatterns[3] = zeroThreeFiveSixNine.first { !it.containsAll(bd) && !it.containsAll(eg) }
    digitsToPatterns[5] = zeroThreeFiveSixNine.first { it.containsAll(bd) && !it.containsAll(eg) && it.size == 5}
    digitsToPatterns[6] = zeroThreeFiveSixNine.first { it.containsAll(bd) && it.containsAll(eg) }
    digitsToPatterns[9] = zeroThreeFiveSixNine.first { it.containsAll(bd) && !it.containsAll(eg) && it.size == 6 }

    return digitsToPatterns.entries.associate { (k, v) -> v to k }
}

private fun outputValue(digits: List<Int>) = digits.fold(0) { n, digit -> n * 10 + digit }