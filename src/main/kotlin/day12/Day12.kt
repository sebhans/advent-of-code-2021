package day12

import trimmedLines

fun solve12_1(input: String) = input.trimmedLines
    .let { lines ->
        CaveSystem.buildFrom(lines)
            .findPaths(Path::simpleStepRule)
            .size
    }

fun solve12_2(input: String) = input.trimmedLines
    .let { lines ->
        CaveSystem.buildFrom(lines)
            .findPaths(Path::extendedStepRule)
            .size
    }

private class Cave(val name: String) {
    val isSmall = name[0].isLowerCase()
    val connectedCaves: MutableSet<Cave> = mutableSetOf()

    fun connectTo(cave: Cave) {
        connectedCaves.add(cave)
    }

    override fun hashCode() = name.hashCode()

    override fun equals(other: Any?) = other is Cave && other.name == name
}

private class Path(val hops: List<Cave>) {
    constructor(start: Cave): this(listOf(start))

    val isAtEnd get() = hops.last().name == "end"

    fun simpleStepRule() = hops.last()
        .connectedCaves
        .filterNot { it.isSmall && hops.contains(it) }
        .map { Path(hops + it) }

    fun extendedStepRule() = hops.last()
        .connectedCaves
        .filterNot { it.name == "start" }
        .filterNot { it.isSmall && hops.contains(it) && hops.alreadyContainsASmallCaveTwice() }
        .map { Path(hops + it) }

    private fun List<Cave>.alreadyContainsASmallCaveTwice() = filter(Cave::isSmall)
        .groupBy(Cave::name)
        .filterValues { it.size > 1 }
        .isNotEmpty()
}

private class CaveSystem(val start: Cave) {
    companion object {
        fun buildFrom(lines: List<String>): CaveSystem {
            val caves = mutableMapOf<String, Cave>()
            lines.forEach {
                val cavePair = it.split('-')
                val first = caves.computeIfAbsent(cavePair[0], ::Cave)
                val second = caves.computeIfAbsent(cavePair[1], ::Cave)
                first.connectTo(second)
                second.connectTo(first)
            }
            return CaveSystem(caves["start"] ?: throw IllegalArgumentException("No start cave found"))
        }
    }

    fun findPaths(stepRule: (Path) -> List<Path>): List<Path> {
        val activePaths = ArrayDeque(listOf(Path(start)))
        val terminatedPaths = mutableListOf<Path>()
        while (activePaths.isNotEmpty()) {
            activePaths.removeFirst()
                .run(stepRule)
                .forEach {
                    if (it.isAtEnd) {
                        terminatedPaths.add(it)
                    } else {
                        activePaths.addLast(it)
                    }
                }
        }
        return terminatedPaths
    }
}
