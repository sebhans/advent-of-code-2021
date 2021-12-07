package days

import asCommaSeparatedInts
import min
import max
import kotlin.math.absoluteValue

fun solve7_1(input: String): Int {
    val crabs = input.asCommaSeparatedInts
    return (crabs.min..crabs.max)
        .map { pos -> crabs.fold(0) { cost, crab -> cost + (pos - crab).absoluteValue } }
        .min
}