package days

import trimmedLines

enum class Direction { forward, down, up }
data class SubmarineCommand(val direction: Direction, val units: Int) {
    companion object {
        fun fromString(s: String) = s.split(' ').let { SubmarineCommand(Direction.valueOf(it[0]), it[1].toInt()) }
    }
}

data class Position(val horizontal: Int, val depth: Int) {
    fun apply(command: SubmarineCommand) =
        when (command.direction) {
            Direction.forward -> copy(horizontal = horizontal + command.units)
            Direction.down -> copy(depth = depth + command.units)
            Direction.up -> copy(depth = depth - command.units)
        }
}

fun solve2_1(input: String) = input.trimmedLines
    .map(SubmarineCommand::fromString)
    .fold(Position(0, 0)) { position, command -> position.apply(command) }
    .let { it.horizontal * it.depth }

data class PositionAndAim(val horizontal: Int, val depth: Int, val aim: Int) {
    fun apply(command: SubmarineCommand) =
        when (command.direction) {
            Direction.forward -> copy(horizontal = horizontal + command.units, depth = depth + aim * command.units)
            Direction.down -> copy(aim = aim + command.units)
            Direction.up -> copy(aim = aim - command.units)
        }
}

fun solve2_2(input: String) = input.trimmedLines
    .map(SubmarineCommand::fromString)
    .fold(PositionAndAim(0, 0, 0)) { positionAndAim, command -> positionAndAim.apply(command) }
    .let { it.horizontal * it.depth }
