package day7

import asCommaSeparatedInts
import min
import max
import kotlin.math.absoluteValue

fun solve7_1(input: String) = crabMovementCost(input.asCommaSeparatedInts) { it }

fun solve7_2(input: String) = crabMovementCost(input.asCommaSeparatedInts, ::sumUpTo)

private fun crabMovementCost(crabs: List<Int>, stepCostFormula: (Int) -> Int) =
    (crabs.min..crabs.max)
        .map { pos -> crabs.fold(0) { cost, crab -> cost + stepCostFormula((pos - crab).absoluteValue) } }
        .min

private fun sumUpTo(n: Int) = n * (n + 1) / 2