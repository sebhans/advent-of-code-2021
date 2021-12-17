package day14

import splitByEmptyLines
import trimmedLines
import java.util.Collections.max
import java.util.Collections.min

fun solve14_1(input: String) = input.splitByEmptyLines.let { lineBlocks ->
    Polymer.fromString(lineBlocks[0])
        .withRules(PairInsertionRuleSet.fromString(lineBlocks[1]))
        .apply { times(10) }
        .then
        .mostCommonMinusLeastCommon
}

fun solve14_2(input: String) = input.splitByEmptyLines.let { lineBlocks ->
    Polymer.fromString(lineBlocks[0])
        .withRules(PairInsertionRuleSet.fromString(lineBlocks[1]))
        .apply { times(40) }
        .then
        .mostCommonMinusLeastCommon
}


private class Polymer(
    private val first: Char,
    private val last: Char,
    private val pairs: MutableMap<Pair<Char, Char>, Long>
) {
    companion object {
        fun fromString(s: String) =
            Polymer(s[0], s[s.lastIndex], s.toList().windowed(2).map { it[0] to it[1] }.toMutableFrequencyMap())
    }

    val mostCommonMinusLeastCommon
        get() =
            pairs.entries.fold(mutableMapOf<Char, Long>()) { counts, entry ->
                counts.apply {
                    compute(entry.key.first, safeAdd(entry.value))
                    compute(entry.key.second, safeAdd(entry.value))
                }
            }.apply {
                compute(first, safeAdd(1))
                compute(last, safeAdd(1))
            }.values.let { (max(it) - min(it)) / 2 }

    fun withRules(ruleSet: PairInsertionRuleSet) = RuleApplication(ruleSet)

    inner class RuleApplication(val ruleSet: PairInsertionRuleSet) {
        val then get() = this@Polymer

        fun times(n: Int) = repeat(n) { _ -> applyOnce(ruleSet) }
    }

    private fun applyOnce(ruleSet: PairInsertionRuleSet) {
        val changes = mutableMapOf<Pair<Char, Char>, Long>()
        ruleSet.forEach {
            val n = pairs[it.matchPair]
            changes.compute(it.matchPair, safeSubtract(n))
            changes.compute(it.matchPair.first to it.toInsert, safeAdd(n))
            changes.compute(it.toInsert to it.matchPair.second, safeAdd(n))
        }
        changes.forEach { pairs.compute(it.key, safeAdd(it.value)) }
    }
}

private fun safeAdd(n: Long?) = { _: Any?, m: Long? -> (m ?: 0) + (n ?: 0) }
private fun safeSubtract(n: Long?) = { _: Any?, m: Long? -> (m ?: 0) - (n ?: 0) }

private fun <T> Iterable<T>.toMutableFrequencyMap() = groupingBy { it }
    .eachCount()
    .mapValues { it.value.toLong() }
    .toMutableMap()

private class PairInsertionRule(val matchPair: Pair<Char, Char>, val toInsert: Char) {
    companion object {
        fun fromString(s: String) = s
            .split(" -> ")
            .let { PairInsertionRule(it[0][0] to it[0][1], it[1][0]) }
    }
}

private class PairInsertionRuleSet(val rules: List<PairInsertionRule>) {
    companion object {
        fun fromString(s: String) = PairInsertionRuleSet(s.trimmedLines.map(PairInsertionRule::fromString))
    }

    fun forEach(f: (PairInsertionRule) -> Unit) = rules.forEach(f)
}