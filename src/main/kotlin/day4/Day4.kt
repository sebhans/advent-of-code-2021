package day4

import IntMatrix
import asCommaSeparatedInts
import asIntMatrix
import first
import fixedPoint
import rest
import splitByEmptyLines

fun solve4_1(input: String) = parseGame(input).play().score

fun solve4_2(input: String) = parseGame(input).playAllBoards().score

private typealias Board = IntMatrix
private val Board.rows get() = this
private val Board.columns get() = (0..first().lastIndex).map { i -> map { it[i] } }
private val Board.allNumbers get() = flatten().toSet()

private data class BingoGame(val calledNumbers: List<Int>, val boards: List<Board>) {
    fun play() = fixedPoint(BingoGameState(this)) { it.playUntil { it.firstBoardHasWon } }

    fun playAllBoards() = fixedPoint(BingoGameState(this)) { it.playUntil { it.lastBoardHasWon } }
}

private data class BingoGameState(val game: BingoGame, val round: Int = 1) {
    fun playUntil(shallStop: (BingoGameState) -> Boolean) = if (shallStop(this)) this else this.copy(round = round + 1)

    val firstBoardHasWon get() = round >= game.calledNumbers.size || game.boards.any { it.hasWon(calledNumbers) }

    val lastBoardHasWon get() = round >= game.calledNumbers.size || game.boards.all { it.hasWon(calledNumbers) }

    val score get() = currentNumber * (winningBoard.allNumbers - calledNumbers).sum()

    private fun calledNumbers(inRound: Int) = game.calledNumbers.take(inRound).toSet()

    private val calledNumbers get() = calledNumbers(round)

    private val currentNumber get() = game.calledNumbers[round - 1]

    private val winningBoard get() =
        game.boards.filter { it.hasWon(calledNumbers) && !it.hasWon(calledNumbers(round - 1)) }.first
}

private fun Board.hasWon(calledNumbers: Set<Int>) =
    rows.any { calledNumbers.containsAll(it) } || columns.any { calledNumbers.containsAll(it) }

private fun parseGame(input: String) = input.splitByEmptyLines.let() {
    BingoGame(it.first.asCommaSeparatedInts, it.rest.map { it.asIntMatrix })
}