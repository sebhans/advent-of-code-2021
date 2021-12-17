package day10

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day10Test {
    private val input = """[({(<(())[]>[[{[]{<()<>>
                           [(()[<>])]({[<{<<[]>>(
                           {([(<{}[<>[]}>{[]{[(<()>
                           (((({<>}<{<{<>}{[]{[]{}
                           [[<[([]))<([[{}[[()]]]
                           [{[{({}]{}}([{[{{{}}([]
                           {<[[]]>}<{[{[{[]{()[[[]
                           [<(<(<(<{}))><([]([]()
                           <{([([[(<>()){}]>(<<{{
                           <{([{{}}[<[[[<>{}]]]>[]]"""

    @Test
    fun `syntax error score is correct`() =
        Assertions.assertEquals(26397, solve10_1(input))

    @Test
    fun `middle score of completions strings is correct`() =
        Assertions.assertEquals(288957, solve10_2(input))
}