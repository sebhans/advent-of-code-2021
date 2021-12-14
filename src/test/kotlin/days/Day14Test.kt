package days

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Day14Test {
    private val input = """NNCB

                           CH -> B
                           HH -> N
                           CB -> H
                           NH -> C
                           HB -> C
                           HC -> B
                           HN -> C
                           NN -> C
                           BH -> H
                           NC -> B
                           NB -> B
                           BN -> B
                           BB -> N
                           BC -> B
                           CC -> N
                           CN -> C"""

    @Test
    fun `most common element - least common element after 10 steps is correct`() =
        assertEquals(1588, solve14_1(input))

    @Test
    fun `most common element - least common element after 40 steps is correct`() =
        assertEquals(2188189693529, solve14_2(input))
}