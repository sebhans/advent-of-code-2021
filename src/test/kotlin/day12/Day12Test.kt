package day12

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day12Test {
    private val input1 = """start-A
                            start-b
                            A-c
                            A-b
                            b-d
                            A-end
                            b-end"""

    private val input2 = """dc-end
                            HN-start
                            start-kj
                            dc-start
                            dc-HN
                            LN-dc
                            HN-end
                            kj-sa
                            kj-HN
                            kj-dc"""

    private val input3 = """fs-end
                            he-DX
                            fs-he
                            start-DX
                            pj-DX
                            end-zg
                            zg-sl
                            zg-pj
                            pj-he
                            RW-he
                            fs-DX
                            pj-RW
                            zg-RW
                            start-pj
                            he-WI
                            zg-he
                            pj-fs
                            start-RW"""

    @Test
    fun `number of paths through the small cave system is correct`() =
        Assertions.assertEquals(10, solve12_1(input1))

    @Test
    fun `number of paths through the slightly larger cave system is correct`() =
        Assertions.assertEquals(19, solve12_1(input2))

    @Test
    fun `number of paths through the even larger cave system is correct`() =
        Assertions.assertEquals(226, solve12_1(input3))

    @Test
    fun `number of paths through the small cave system with the new rules is correct`() =
        Assertions.assertEquals(36, solve12_2(input1))

    @Test
    fun `number of paths through the slightly larger cave system with the new rules is correct`() =
        Assertions.assertEquals(103, solve12_2(input2))

    @Test
    fun `number of paths through the even larger cave system with the new rules is correct`() =
        Assertions.assertEquals(3509, solve12_2(input3))
}