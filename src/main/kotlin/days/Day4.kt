package days

import IntMatrix
import asCommaSeparatedInts
import asIntMatrix
import first
import fixedPoint
import rest
import splitByEmptyLines

fun solve4_1(input: String) = parseGame(input)
    .play()
    .let {
        it.currentNumber * (it.winningBoard.allNumbers - it.calledNumbers).sum()
    }

private typealias Board = IntMatrix
private val Board.rows get() = this
private val Board.columns get() = (0..first().lastIndex).map { i -> map { it[i] } }
private val Board.allNumbers get() = flatten().toSet()

private data class BingoGame(val calledNumbers: List<Int>, val boards: List<Board>) {
    fun play() = fixedPoint(BingoGameState(this)) { it.play() }
}

private data class BingoGameState(val game: BingoGame, val round: Int = 1) {
    fun play() = if (isOver) this else this.copy(round = round + 1)

    private val isOver get() =
        round >= game.calledNumbers.size || game.boards.any { it.hasWon(calledNumbers) }

    val calledNumbers get() = game.calledNumbers.take(round).toSet()

    val currentNumber get() = game.calledNumbers[round - 1]

    val winningBoard get() = game.boards.first { it.hasWon(calledNumbers) }
}

private fun Board.hasWon(calledNumbers: Set<Int>) =
    rows.any { calledNumbers.containsAll(it) } || columns.any { calledNumbers.containsAll(it) }

private fun parseGame(input: String) = input.splitByEmptyLines.let() {
    BingoGame(it.first.asCommaSeparatedInts, it.rest.map { it.asIntMatrix })
}
