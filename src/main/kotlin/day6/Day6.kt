package day6

import asCommaSeparatedInts

fun solve6_1(input: String) = LanternfishPopulation.of(input.asCommaSeparatedInts)
    .passDays(80)
    .size

fun solve6_2(input: String) = LanternfishPopulation.of(input.asCommaSeparatedInts)
    .passDays(256)
    .size

class LanternfishPopulation(val fishesPerTimer: LongArray) {
    companion object {
        fun of(initialTimers: List<Int>) =
            LanternfishPopulation(LongArray(9).apply { initialTimers.forEach { this[it]++ } })
    }

    fun passDay() = LanternfishPopulation(
        longArrayOf(
            fishesPerTimer[1],
            fishesPerTimer[2],
            fishesPerTimer[3],
            fishesPerTimer[4],
            fishesPerTimer[5],
            fishesPerTimer[6],
            fishesPerTimer[7] + fishesPerTimer[0],
            fishesPerTimer[8],
            fishesPerTimer[0]
        )
    )

    fun passDays(n: Long) = (1..n).fold(this) { population, _ -> population.passDay() }

    val size get() = fishesPerTimer.sum()
}