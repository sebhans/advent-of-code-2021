package days

import trimmedLines
import java.util.*

fun solve10_1(input: String) = input.trimmedLines
    .map(::scanChunks)
    .sumOf(ParseState::syntaxErrorScore)

fun solve10_2(input: String) = input.trimmedLines
    .map(::scanChunks)
    .filterNot(ParseState::hasSyntaxError)
    .map(ParseState::completionStringScore)
    .sorted()
    .let { it[it.lastIndex / 2] }

private fun scanChunks(line: String) =
    ParseState().apply {
        line.forEach { update(it) }
    }

private class ParseState {
    val chunkStack: Deque<Char> = ArrayDeque()
    var syntaxError: Char? = null

    fun update(c: Char) {
        if (hasSyntaxError) return
        when (c) {
            '(' -> chunkStack.addFirst(')')
            '[' -> chunkStack.addFirst(']')
            '{' -> chunkStack.addFirst('}')
            '<' -> chunkStack.addFirst('>')
            else -> if (c == chunkStack.peekFirst()) chunkStack.removeFirst() else syntaxError = c
        }
    }

    fun syntaxErrorScore(): Long = when (syntaxError) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }

    val hasSyntaxError get() = syntaxError != null

    fun completionStringScore() = chunkStack.fold(0L) { score, c ->
        score * 5 + when (c) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }
}