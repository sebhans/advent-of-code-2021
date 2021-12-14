import java.lang.reflect.Modifier
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.kotlinFunction
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
    println("Advent of Code 2021")
    for (day in 1..24) {
        val input = readInput("input$day.txt")
        println("Day $day:")
        daySolver(day, 1).printResultFor(input)
        daySolver(day, 2).printResultFor(input)
    }
}

private fun readInput(filename: String) = currentClass().getResource(filename)?.readText(Charsets.UTF_8) ?: ""

private fun daySolver(day: Int, puzzle: Int) =
    try {
        currentClass()
            .classLoader
            .loadClass("days.Day${day}Kt")
            .methods
            .find { it.name == "solve${day}_$puzzle" && Modifier.isStatic(it.modifiers) }
            ?.kotlinFunction
    } catch (e: ClassNotFoundException) {
        null
    }

private fun KFunction<*>?.printResultFor(input: String) {
    var result: Any?
    val time = measureTimeMillis {
        result = this?.call(input)
    }
    if (result != null) {
        println("    $result ($time ms)")
    }
}

private fun currentClass(): Class<*> = ::currentClass.javaMethod!!.declaringClass