package days

import asDigitList
import trimmedLines

const val OCTOPUS_MATRIX_SIZE = 10

fun solve11_1(input: String) = input.trimmedLines
    .map(String::asDigitList)
    .let(::OctopusMatrix)
    .apply { repeat(100) { step() } }
    .flashCount

fun solve11_2(input: String) = input.trimmedLines
    .map(String::asDigitList)
    .let(::OctopusMatrix)
    .run {
        while (!wasSynchronizedFlash) step()
        stepCount
    }

private class OctopusMatrix(matrix: List<List<Int>>) {
    val octopuses = Array(OCTOPUS_MATRIX_SIZE * OCTOPUS_MATRIX_SIZE) {
        matrix[it / OCTOPUS_MATRIX_SIZE][it % OCTOPUS_MATRIX_SIZE]
    }
    var flashCount = 0L
    var stepCount = 0L
    var wasSynchronizedFlash = false

    fun step() {
        var flashes = mutableListOf<Int>()
        var flashesInStep = 0
        (0..octopuses.lastIndex).forEach {
            if (++octopuses[it] > 9) {
                flashes.add(it)
                flashesInStep++
                octopuses[it] = 0
            }
        }

        while (flashes.isNotEmpty()) {
            val nextFlashes = mutableListOf<Int>()
            flashes.flatMap { neighboursOf(it) }.forEach {
                if (octopuses[it] > 0 && ++octopuses[it] > 9) {
                    nextFlashes.add(it)
                    flashesInStep++
                    octopuses[it] = 0
                }
            }
            flashes = nextFlashes
        }

        flashCount += flashesInStep
        stepCount++
        wasSynchronizedFlash = flashesInStep == octopuses.size
    }

    private fun neighboursOf(index: Int) = buildList {
        val notTopMargin = index >= OCTOPUS_MATRIX_SIZE
        val notLeftMargin = index % OCTOPUS_MATRIX_SIZE > 0
        val notRightMargin = index % OCTOPUS_MATRIX_SIZE < OCTOPUS_MATRIX_SIZE - 1
        val notBottomMargin = index < OCTOPUS_MATRIX_SIZE * (OCTOPUS_MATRIX_SIZE - 1)

        if (notTopMargin) {
            if (notLeftMargin) add(index - OCTOPUS_MATRIX_SIZE - 1)
            add(index - OCTOPUS_MATRIX_SIZE)
            if (notRightMargin) add(index - OCTOPUS_MATRIX_SIZE + 1)
        }

        if (notLeftMargin) add(index - 1)
        if (notRightMargin) add(index + 1)

        if (notBottomMargin) {
            if (notLeftMargin) add(index + OCTOPUS_MATRIX_SIZE - 1)
            add(index + OCTOPUS_MATRIX_SIZE)
            if (notRightMargin) add(index + OCTOPUS_MATRIX_SIZE + 1)
        }
    }
}