package days

import org.junit.jupiter.api.Assertions.assertEquals
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
        assertEquals(26397, solve10_1(input))

    @Test
    fun `middle score of completions strings is correct`() =
        assertEquals(288957, solve10_2(input))
}