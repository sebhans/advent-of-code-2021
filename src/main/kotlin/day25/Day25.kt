package day25

import trimmedLines

fun solve25_1(input: String) = input.trimmedLines
    .map { it.split("").filter(String::isNotEmpty).map { it[0] } }
    .let { generateSequence(State(it), State::step) }
    .last()
    .step

typealias SeaFloor = List<List<Char>>

data class State(val seaFloor: SeaFloor, val step: Int = 0, val stopped: Boolean = false) {
    fun step(): State? =
        if (stopped) null
        else stepSouthFacing(stepEastFacing(seaFloor)).let { State(it, step + 1, it == seaFloor) }

    private fun stepEastFacing(seaFloor: SeaFloor) =
        seaFloor.map { line ->
            buildList {
                (0..line.lastIndex).forEach { i ->
                    add(
                        when {
                            line[i] == '>' && line[(i + 1) % line.size] == '.' -> '.'
                            line[(i + line.size - 1) % line.size] == '>' && line[i] == '.' -> '>'
                            else -> line[i]
                        }
                    )
                }
            }
        }

    private fun stepSouthFacing(seaFloor: SeaFloor) = buildList {
        (0..seaFloor.lastIndex).forEach { j ->
            add(buildList {
                (0..seaFloor[j].lastIndex).forEach { i ->
                    add(
                        when {
                            seaFloor[j][i] == 'v' && seaFloor[(j + 1) % seaFloor.size][i] == '.' -> '.'
                            seaFloor[(j + seaFloor.size - 1) % seaFloor.size][i] == 'v' && seaFloor[j][i] == '.' -> 'v'
                            else -> seaFloor[j][i]
                        }
                    )
                }
            })
        }
    }
}