package day21

import trimmedLines
import kotlin.math.max
import kotlin.math.min

fun solve21_1(input: String) = Game.fromString(input).play().let { it.losingScore * it.turns }

class Game(
    val player1: Player,
    val player2: Player,
    val turns: Int = 0,
    val onesTurn: Boolean = true,
    val dieState: Int = -1
) {
    companion object {
        fun fromString(s: String) = s.toPlayers().let { Game(it[0], it[1]) }

        const val WINNING_SCORE = 1000
    }

    val losingScore get() = min(player1.score, player2.score)

    private val isWon get() = player1.hasWon(WINNING_SCORE) || player2.hasWon(WINNING_SCORE)

    fun makeMove() = (dieState + 3).let { // can ignore wrapping because someone wins before we reach 100
        Game(
            if (onesTurn) player1.move(it * 3) else player1,
            if (!onesTurn) player2.move(it * 3) else player2,
            turns + 3,
            !onesTurn,
            it
        )
    }

    fun play() = generateSequence(this, Game::makeMove)
        .filter(Game::isWon)
        .first()
}

class Player(val position: Int, val score: Int = 0) {
    fun hasWon(winningScore: Int) = score >= winningScore

    fun move(steps: Int) = ((position - 1 + steps) % 10 + 1).let { Player(it, score + it) }
}

val PLAYER_REGEX = Regex("\\d+$")

fun String.toPlayers() = trimmedLines.map {
    Player(PLAYER_REGEX.find(it)?.value?.toInt() ?: throw IllegalArgumentException("cannot parse game"))
}

fun solve21_2(input: String) = QuantumGame.fromString(input).play().let { max(it.first, it.second) }

class QuantumGame(private val player1: Player, private val player2: Player) {
    companion object {
        fun fromString(s: String) = s.toPlayers().let { QuantumGame(it[0], it[1]) }

        const val WINNING_SCORE = 21
    }

    fun play() = generateSequence(UniverseSet(listOf(Universe(player1, player2))), UniverseSet::makeMove)
        .filter(UniverseSet::allDecided)
        .first()
        .let { it.wonUniverses1 to it.wonUniverses2 }

    private class UniverseSet(
        private val activeUniverses: List<Universe>,
        val wonUniverses1: Long = 0,
        val wonUniverses2: Long = 0,
        private val onesTurn: Boolean = true
    ) {
        val allDecided get() = activeUniverses.isEmpty()

        fun makeMove() = activeUniverses.flatMap { it.makeQuantumMove(onesTurn) }
            .partition(Universe::isWon)
            .let { byWon ->
                byWon.first.partition(Universe::isWonByPlayer1).let { byWinner ->
                    UniverseSet(
                        byWon.second.groupBy(Universe::key).map { it.value.reduce(Universe::merge) },
                        wonUniverses1 + byWinner.first.sumOf(Universe::multiplicity),
                        wonUniverses2 + byWinner.second.sumOf(Universe::multiplicity),
                        !onesTurn
                    )
                }
            }
    }

    private class Universe(private val player1: Player, private val player2: Player, val multiplicity: Long = 1) {
        val isWonByPlayer1 get() = player1.hasWon(WINNING_SCORE)

        val isWon get() = player1.hasWon(WINNING_SCORE) || player2.hasWon(WINNING_SCORE)

        val key get() = Key(player1.position, player1.score, player2.position, player2.score)

        fun makeQuantumMove(onesTurn: Boolean) = listOf(
            move(onesTurn, 3, 1),
            move(onesTurn, 4, 3),
            move(onesTurn, 5, 6),
            move(onesTurn, 6, 7),
            move(onesTurn, 7, 6),
            move(onesTurn, 8, 3),
            move(onesTurn, 9, 1),
        )

        private fun move(onesTurn: Boolean, eyes: Int, multiplicity: Int) = Universe(
            if (onesTurn) player1.move(eyes) else player1,
            if (!onesTurn) player2.move(eyes) else player2,
            this.multiplicity * multiplicity
        )

        fun merge(other: Universe) =
            if (key == other.key) Universe(player1, player2, multiplicity + other.multiplicity)
            else throw IllegalStateException("trying to merge different universes")

        data class Key(val player1Position: Int, val player1Score: Int, val player2position: Int, val player2Score: Int)
    }
}