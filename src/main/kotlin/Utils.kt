val String.trimmedLines get() = this.lines().map(String::trim)

val <T> Iterable<T>.first get() = first()
val <T> Iterable<T>.rest get() = drop(1)
