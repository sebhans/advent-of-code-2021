package days

import asCommaSeparatedInts
import splitByEmptyLines
import trimmedLines
import kotlin.math.max

fun solve13_1(input: String) = input.splitByEmptyLines
    .let {
        val dots = it[0].trimmedLines.map(Dot::fromString).toSet()
        val foldingInstructions = it[1].trimmedLines.map(FoldingInstruction::fromString)
        foldingInstructions[0]
            .apply(dots)
            .size
    }

fun solve13_2(input: String) = input.splitByEmptyLines
    .let {
        val dots = it[0].trimmedLines.map(Dot::fromString).toSet()
        val foldingInstructions = it[1].trimmedLines.map(FoldingInstruction::fromString)
        foldingInstructions
            .fold(dots) { dots, instruction -> instruction.apply(dots) }
            .renderDots()
    }

private data class Dot(val x: Int, val y: Int) {
    companion object {
        fun fromString(s: String) = s.asCommaSeparatedInts.let { Dot(it[0], it[1]) }
    }
}

private sealed class FoldingInstruction(val foldingLine: Int) {
    companion object {
        fun fromString(s: String) = s.split('=').let {
            when (it[0].last()) {
                'x' -> VerticalFoldingInstruction(it[1].toInt())
                'y' -> HorizontalFoldingInstruction(it[1].toInt())
                else -> throw IllegalArgumentException("unknown axis: ${it[0].last()}")
            }
        }
    }

    abstract fun apply(dots: Set<Dot>): Set<Dot>
}

private class VerticalFoldingInstruction(foldingLine: Int): FoldingInstruction(foldingLine) {
    override fun apply(dots: Set<Dot>) = dots
        .map {
            if (it.x > foldingLine) Dot(foldingLine - (it.x - foldingLine), it.y)
            else it
        }.toSet()
}

private class HorizontalFoldingInstruction(foldingLine: Int): FoldingInstruction(foldingLine) {
    override fun apply(dots: Set<Dot>) = dots
        .map {
            if (it.y > foldingLine) Dot(it.x, foldingLine - (it.y - foldingLine))
            else it
        }.toSet()
}

private fun Set<Dot>.renderDots(): String {
    val canvas = StringBuilder("\n")
    val (maxX, maxY) = fold(Dot(0, 0)) { acc, dot -> Dot(max(acc.x, dot.x), max(acc.y, dot.y)) }
    for (y in 0..maxY) {
        for (x in 0..maxX) {
            canvas.append(if (Dot(x, y) in this) '#' else '.')
        }
        canvas.append('\n')
    }
    return canvas.toString()
}