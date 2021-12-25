package day23

fun solve23_1(input: String) = input.lines().let { lines ->
    val burrow = Burrow(
        listOf(Amphipod.valueOf(lines[3].slice(3..3)), Amphipod.valueOf(lines[2].slice(3..3))),
        listOf(Amphipod.valueOf(lines[3].slice(5..5)), Amphipod.valueOf(lines[2].slice(5..5))),
        listOf(Amphipod.valueOf(lines[3].slice(7..7)), Amphipod.valueOf(lines[2].slice(7..7))),
        listOf(Amphipod.valueOf(lines[3].slice(9..9)), Amphipod.valueOf(lines[2].slice(9..9)))
    )
    burrow.arrange()?.let {
        print(it.first)
        it.second
    } ?: throw IllegalStateException("burrow appears to be unsolvable")
}

fun solve23_2(input: String) = input.lines().let { lines ->
    val burrow = Burrow(
        listOf(Amphipod.valueOf(lines[3].slice(3..3)), Amphipod.D, Amphipod.D, Amphipod.valueOf(lines[2].slice(3..3))),
        listOf(Amphipod.valueOf(lines[3].slice(5..5)), Amphipod.B, Amphipod.C, Amphipod.valueOf(lines[2].slice(5..5))),
        listOf(Amphipod.valueOf(lines[3].slice(7..7)), Amphipod.A, Amphipod.B, Amphipod.valueOf(lines[2].slice(7..7))),
        listOf(Amphipod.valueOf(lines[3].slice(9..9)), Amphipod.C, Amphipod.A, Amphipod.valueOf(lines[2].slice(9..9)))
    )
    burrow.arrange()?.let {
        print(it.first)
        it.second
    } ?: throw IllegalStateException("burrow appears to be unsolvable")
}

enum class Amphipod(val movementEnergy: Int) {
    A(1),
    B(10),
    C(100),
    D(1000)
}

class Burrow(roomA: List<Amphipod>, roomB: List<Amphipod>, roomC: List<Amphipod>, roomD: List<Amphipod>) {
    private val hallway = listOf(
        HallwayPosition("Hall 0"),
        HallwayPosition("Hall 1"),
        HallwayPosition("Hall 3"),
        HallwayPosition("Hall 5"),
        HallwayPosition("Hall 7"),
        HallwayPosition("Hall 9"),
        HallwayPosition("Hall A")
    )

    private val rooms = listOf(
        RoomPosition("Room A", Amphipod.A, roomA.toMutableList()),
        RoomPosition("Room B", Amphipod.B, roomB.toMutableList()),
        RoomPosition("Room C", Amphipod.C, roomC.toMutableList()),
        RoomPosition("Room D", Amphipod.D, roomD.toMutableList()),
    )

    private val allPositions = hallway + rooms

    private var depth = 0
    private val maxDepth = (roomA.size + roomB.size + roomC.size + roomD.size) * 2

    init {
        hallway[0].paths.add(Path(rooms[0], 3, listOf(hallway[1])))
        hallway[0].paths.add(Path(rooms[1], 5, listOf(hallway[1], hallway[2])))
        hallway[0].paths.add(Path(rooms[2], 7, listOf(hallway[1], hallway[2], hallway[3])))
        hallway[0].paths.add(Path(rooms[3], 9, listOf(hallway[1], hallway[2], hallway[3], hallway[4])))

        hallway[1].paths.add(Path(rooms[0], 2, emptyList()))
        hallway[1].paths.add(Path(rooms[1], 4, listOf(hallway[2])))
        hallway[1].paths.add(Path(rooms[2], 6, listOf(hallway[2], hallway[3])))
        hallway[1].paths.add(Path(rooms[3], 8, listOf(hallway[2], hallway[3], hallway[4])))

        hallway[2].paths.add(Path(rooms[0], 2, emptyList()))
        hallway[2].paths.add(Path(rooms[1], 2, emptyList()))
        hallway[2].paths.add(Path(rooms[2], 4, listOf(hallway[3])))
        hallway[2].paths.add(Path(rooms[3], 6, listOf(hallway[3], hallway[4])))

        hallway[3].paths.add(Path(rooms[1], 2, emptyList()))
        hallway[3].paths.add(Path(rooms[2], 2, emptyList()))
        hallway[3].paths.add(Path(rooms[0], 4, listOf(hallway[2])))
        hallway[3].paths.add(Path(rooms[3], 4, listOf(hallway[4])))

        hallway[4].paths.add(Path(rooms[3], 2, emptyList()))
        hallway[4].paths.add(Path(rooms[2], 2, emptyList()))
        hallway[4].paths.add(Path(rooms[1], 4, listOf(hallway[3])))
        hallway[4].paths.add(Path(rooms[0], 6, listOf(hallway[3], hallway[2])))

        hallway[5].paths.add(Path(rooms[3], 2, emptyList()))
        hallway[5].paths.add(Path(rooms[2], 4, listOf(hallway[4])))
        hallway[5].paths.add(Path(rooms[1], 6, listOf(hallway[4], hallway[3])))
        hallway[5].paths.add(Path(rooms[0], 8, listOf(hallway[4], hallway[3], hallway[2])))

        hallway[6].paths.add(Path(rooms[3], 3, listOf(hallway[5])))
        hallway[6].paths.add(Path(rooms[2], 5, listOf(hallway[5], hallway[4])))
        hallway[6].paths.add(Path(rooms[1], 7, listOf(hallway[5], hallway[4], hallway[3])))
        hallway[6].paths.add(Path(rooms[0], 9, listOf(hallway[5], hallway[4], hallway[3], hallway[2])))

        for (room in rooms) {
            room.paths.addAll(hallway
                .flatMap { position -> position.paths.map { position to it } }
                .filter { it.second.destination === room }
                .sortedBy { it.second.distance }
                .map { Path(it.first, it.second.distance, it.second.interveningPositions) })
        }
    }

    private fun isOccupiedCorrectly() = rooms.all(RoomPosition::isOccupiedCorrectly)

    fun arrange(): Pair<String, Int>? {
        if (depth >= maxDepth) {
            return null
        }
        depth++
        val movements = mutableListOf<Pair<String,Int>>()
        for (position in allPositions) {
            position.amphipodWhoWantsToMove?.also { amphipod ->
                for (path in position.paths) {
                    if (path.destination.willAccept(amphipod) && path.isFree()) {
                        val movementEnergy =
                            (path.distance + path.destination.accept(amphipod) + position.evacuate()) * amphipod.movementEnergy
                        val movement = "$amphipod from $position to ${path.destination} for $movementEnergy\n"

                        if (isOccupiedCorrectly()) {
                            movements.add(movement to movementEnergy)
                        } else {
                            arrange()?.also { movements.add(movement + it.first to movementEnergy + it.second) }
                        }

                        path.destination.evacuate()
                        position.accept(amphipod)
                    }
                }
            }
        }
        depth--

        if (movements.isEmpty()) {
            return null
        }

        return movements.minByOrNull { it.second }
    }
}

class Path(val destination: Position, val distance: Int, val interveningPositions: List<HallwayPosition>) {
    fun isFree() = interveningPositions.all(HallwayPosition::isFree)
}

sealed class Position(private val name: String) {
    val paths = mutableListOf<Path>()

    abstract val amphipodWhoWantsToMove: Amphipod?

    abstract fun willAccept(amphipod: Amphipod): Boolean

    abstract fun accept(amphipod: Amphipod): Int

    abstract fun evacuate(): Int

    override fun toString() = name
}

class HallwayPosition(name: String, private var amphipod: Amphipod? = null) : Position(name) {
    override val amphipodWhoWantsToMove: Amphipod? get() = amphipod

    override fun willAccept(amphipod: Amphipod) = this.amphipod == null

    override fun accept(amphipod: Amphipod): Int {
        this.amphipod = amphipod
        return 0
    }

    override fun evacuate(): Int {
        amphipod = null
        return 0
    }

    fun isFree() = amphipod == null
}

class RoomPosition(name: String, private val targetType: Amphipod, private val amphipods: MutableList<Amphipod>) :
    Position(name) {
    private val roomSize = amphipods.size

    override val amphipodWhoWantsToMove: Amphipod? get() =
        amphipods.lastOrNull().takeIf { amphipods.any { it != targetType } }

    override fun willAccept(amphipod: Amphipod) =
        amphipod == targetType && amphipods.all { it == amphipod } && amphipods.size < roomSize

    override fun accept(amphipod: Amphipod): Int {
        amphipods.add(amphipod)
        return roomSize - amphipods.size
    }

    override fun evacuate(): Int {
        amphipods.removeLast()
        return roomSize - amphipods.size - 1
    }

    fun isOccupiedCorrectly() = amphipods.size == roomSize && amphipods.all { it == targetType }
}