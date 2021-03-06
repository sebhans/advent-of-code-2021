val String.trimmedLines get() = lines().map(String::trim)
val String.splitByEmptyLines get() = split("\n\n")
val String.asCommaSeparatedInts get() = split(',').map(String::toInt)
val String.asWhitespaceSeparatedInts get() = trim().split(Regex("\\s+")).map(String::toInt)
val String.asIntMatrix get() = lines().filter(String::isNotBlank).map { it.asWhitespaceSeparatedInts }
val String.asDigitList get() = split("").filter(String::isNotBlank).map(String::toInt)
val String.asDigitMatrix get() = lines().map(String::asDigitList)

val <T> Iterable<T>.first get() = first()
val <T> Iterable<T>.rest get() = drop(1)

fun <T> fixedPoint(x: T, f: (T) -> T): T = f(x).let { next -> if (next == x) x else fixedPoint(next, f) }

typealias IntMatrix = List<List<Int>>

val List<Int>.min get() = reduce { n, m -> if (n < m) n else m }
val List<Int>.max get() = reduce { n, m -> if (n > m) n else m }

data class Coordinate(val x: Int, val y: Int)

data class Coordinate3(val x: Int, val y: Int, val z: Int)