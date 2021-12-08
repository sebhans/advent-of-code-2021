import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class UtilsTest {

    @Test
    fun `splitByEmptyLines splits correctly`() {
        assertEquals(listOf("a", "b\nc"), ("a\n\nb\nc").splitByEmptyLines)
    }

    @Test
    fun `asCommaSeparatedNumbers yields correct numbers`() {
        assertEquals(listOf(1, 2, 3), ("1,2,3").asCommaSeparatedInts)
    }

    @Test
    fun `asWhitespaceSeparatedNumbers yields correct numbers for space-separated string`() {
        assertEquals(listOf(1, 2, 3), ("1 2 3").asWhitespaceSeparatedInts)
    }

    @Test
    fun `asWhitespaceSeparatedNumbers ignores leading whitespace`() {
        assertEquals(listOf(1), (" 1").asWhitespaceSeparatedInts)
    }

    @Test
    fun `asWhitespaceSeparatedNumbers ignores trailing whitespace`() {
        assertEquals(listOf(1), ("1 ").asWhitespaceSeparatedInts)
    }

    @Test
    fun `asWhitespaceSeparatedNumbers yields correct numbers for multi-space-separated string`() {
        assertEquals(listOf(1, 2, 3), ("1  2       3").asWhitespaceSeparatedInts)
    }

    @Test
    fun `asIntMatrix yields correct matrix`() {
        assertEquals(
            listOf(
                listOf(1, 2, 3),
                listOf(4, 5, 6),
                listOf(7, 8, 9)),
            ("1  2 3 \n4  5 6\n 7 8 9  ").asIntMatrix
        )
    }
}