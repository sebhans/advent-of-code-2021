package day18

import trimmedLines
import kotlin.math.max

fun solve18_1(input: String) = input.trimmedLines
    .map(SnailfishNumber::fromString)
    .reduce(SnailfishNumber::add)
    .magnitude

fun solve18_2(input: String) = input.trimmedLines
    .map(SnailfishNumber::fromString)
    .let {
        var largestMagnitude = 0
        for (i in 0..it.lastIndex) {
            for (j in 0..it.lastIndex) {
                if (i != j) {
                    largestMagnitude =
                        max(largestMagnitude, max(it[i].add(it[j]).magnitude, it[j].add(it[i]).magnitude))
                }
            }
        }
        largestMagnitude
    }

data class SnailfishNumber(val pair: SnailfishPair) {
    companion object {
        fun fromString(s: String) = s.fold(ArrayDeque<SnailfishElement>()) { stack, c ->
            when (c) {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> stack.addFirst(SnailfishLeaf(c.toString().toInt()))
                ']' -> stack.removeFirst().let { stack.addFirst(SnailfishPair(stack.removeFirst(), it)) }
            }
            stack
        }.removeFirst().let { SnailfishNumber(it as SnailfishPair) }
    }

    val magnitude get() = pair.magnitude

    fun add(n: SnailfishNumber) = SnailfishNumber(SnailfishPair(pair, n.pair)).reduce()

    fun reduce() = SnailfishNumber(generateSequence(pair, SnailfishPair::reduceOnce).last())
}

sealed class SnailfishElement {
    abstract val magnitude: Int

    internal open fun maybeExplode(depth: Int): ExplosionResult? = null
    internal abstract fun maybeSplit(): SnailfishElement?

    internal abstract fun addValueLeft(n: Int): SnailfishElement
    internal abstract fun addValueRight(n: Int): SnailfishElement

    internal data class ExplosionResult(val explodedPair: SnailfishElement, val left: Int?, val right: Int?)
}

data class SnailfishLeaf(val value: Int) : SnailfishElement() {
    override val magnitude: Int get() = value

    override fun maybeSplit() =
        if (value >= 10) SnailfishPair(SnailfishLeaf(value / 2), SnailfishLeaf(value - value / 2))
        else null

    override fun addValueLeft(n: Int) = SnailfishLeaf(value + n)
    override fun addValueRight(n: Int) = SnailfishLeaf(value + n)

    override fun toString() = value.toString()
}

data class SnailfishPair(val left: SnailfishElement, val right: SnailfishElement) : SnailfishElement() {
    override val magnitude: Int get() = 3 * left.magnitude + 2 * right.magnitude

    internal fun reduceOnce() = (maybeExplode() ?: maybeSplit()) as SnailfishPair?

    private fun maybeExplode() = maybeExplode(0)?.explodedPair

    override fun maybeExplode(depth: Int): ExplosionResult? {
        if (depth >= 4) {
            return ExplosionResult(SnailfishLeaf(0), (left as SnailfishLeaf).value, (right as SnailfishLeaf).value)
        }
        left.maybeExplode(depth + 1)?.also {
            return ExplosionResult(
                SnailfishPair(
                    it.explodedPair,
                    if (it.right != null) right.addValueLeft(it.right) else right
                ), it.left, null
            )
        }
        right.maybeExplode(depth + 1)?.also {
            return ExplosionResult(
                SnailfishPair(
                    if (it.left != null) left.addValueRight(it.left) else left,
                    it.explodedPair
                ), null, it.right
            )
        }
        return null
    }

    override fun addValueLeft(n: Int) = SnailfishPair(left.addValueLeft(n), right)

    override fun addValueRight(n: Int) = SnailfishPair(left, right.addValueRight(n))

    override fun maybeSplit(): SnailfishElement? {
        left.maybeSplit()?.also { return SnailfishPair(it, right) }
        right.maybeSplit()?.also { return SnailfishPair(left, it) }
        return null
    }

    override fun toString() = "[$left,$right]"
}