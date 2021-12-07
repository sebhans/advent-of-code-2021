val String.trimmedLines get() = lines().map(String::trim)
val String.splitByEmptyLines get() = split("\n\n")
val String.asCommaSeparatedInts get() = split(',').map(String::toInt)
val String.asWhitespaceSeparatedInts get() = trim().split(Regex("\\s+")).map(String::toInt)
val String.asIntMatrix get() = lines().filter(String::isNotBlank).map { it.asWhitespaceSeparatedInts }

val <T> Iterable<T>.first get() = first()
val <T> Iterable<T>.rest get() = drop(1)

fun <T> fixedPoint(x: T, f: (T) -> T): T = f(x).let { next -> if (next == x) x else fixedPoint(next, f) }

typealias IntMatrix = List<List<Int>>

val List<Int>.min get() = reduce { n, m -> if (n < m) n else m }
val List<Int>.max get() = reduce { n, m -> if (n > m) n else m }
