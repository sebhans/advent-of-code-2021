package days

import trimmedLines

fun solve3_1(input: String) = input.trimmedLines
    .map(::toBits)
    .let { diagnostics ->
        val gammaRate = diagnostics
            .reduce(::addBits)
            .map(oneIfAtLeastHalfOf(diagnostics.size))
            .toInt()
        val epsilonRate = gammaRate.invertBitsUpTo(diagnostics[0].size)
        gammaRate * epsilonRate
    }

fun solve3_2(input: String) = input.trimmedLines
    .map(::toBits)
    .let {
        val oxygenGeneratorRating = it.reduceBy(::mostCommonBit).toInt()
        val co2ScrubberRating = it.reduceBy(::leastCommonBit).toInt()
        oxygenGeneratorRating * co2ScrubberRating
    }

typealias Bits = List<Int>

private fun toBits(s: String) = s.chunked(1).map { it.toInt(2) }
private fun Bits.toInt() = reduce { n, bit -> n * 2 + bit }

private fun addBits(a: Bits, b: Bits) = a.zip(b).map { it.first + it.second }

private fun oneIfAtLeastHalfOf(n: Int) = { m: Int -> if (2 * m >= n) 1 else 0 }

private fun Int.invertBitsUpTo(n: Int) = this xor ((1 shl n) - 1)

typealias BitFilterFunction = (Int, Int) -> Boolean
private fun mostCommonBit(bit: Int, mostCommonBit: Int) = bit == mostCommonBit
private fun leastCommonBit(bit: Int, mostCommonBit: Int) = bit != mostCommonBit

private fun List<Bits>.reduceBy(bitFilter: BitFilterFunction, position: Int = 0 ): Bits =
    if (size == 1) this[0]
    else filteredByBit(bitFilter, position).reduceBy(bitFilter, position + 1)

private fun List<Bits>.filteredByBit(bitFilter: BitFilterFunction, position: Int): List<Bits> {
    val mostCommonBit = mostCommonBitAt(position)
    return filter { bitFilter(it[position], mostCommonBit) }
}

private fun List<Bits>.mostCommonBitAt(position: Int) =
    fold(0) { numberOfOnes, bits -> numberOfOnes + bits[position] }
        .let(oneIfAtLeastHalfOf(size))