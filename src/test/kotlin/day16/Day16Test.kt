package day16

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class Day16Test {
    private val input = """1163751742
                           1381373672
                           2136511328
                           3694931569
                           7463417111
                           1319128137
                           1359912421
                           3125421639
                           1293138521
                           2311944581"""

    @Test
    fun `decoding the BITS structure works - example 1`() =
        Assertions.assertEquals(6, solve16_1("D2FE28"))

    @Test
    fun `decoding the BITS structure works - example 2`() =
        Assertions.assertEquals(9, solve16_1("38006F45291200"))

    @Test
    fun `decoding the BITS structure works - example 3`() =
        Assertions.assertEquals(14, solve16_1("EE00D40C823060"))

    @Test
    fun `decoding the BITS structure works - example 4`() =
        Assertions.assertEquals(16, solve16_1("8A004A801A8002F478"))

    @Test
    fun `decoding the BITS structure works - example 5`() =
        Assertions.assertEquals(12, solve16_1("620080001611562C8802118E34"))

    @Test
    fun `decoding the BITS structure works - example 6`() =
        Assertions.assertEquals(23, solve16_1("C0015000016115A2E0802F182340"))

    @Test
    fun `decoding the BITS structure works - example 7`() =
        Assertions.assertEquals(31, solve16_1("A0016C880162017C3686B18A3D4780"))

    @Test
    fun `literal value packet works`() = Assertions.assertEquals(2021, solve16_2("D2FE28"))

    @Test
    fun `less than operator works for example 2`() = Assertions.assertEquals(1, solve16_2("38006F45291200"))

    @Test
    fun `maximum operator works for example 3`() = Assertions.assertEquals(3, solve16_2("EE00D40C823060"))

    @Test
    fun `sum packet works`() = Assertions.assertEquals(3, solve16_2("C200B40A82"))

    @Test
    fun `product packet works`() = Assertions.assertEquals(54, solve16_2("04005AC33890"))

    @Test
    fun `minimum packet works`() = Assertions.assertEquals(7, solve16_2("880086C3E88112"))

    @Test
    fun `maximum packet works`() = Assertions.assertEquals(9, solve16_2("CE00C43D881120"))

    @Test
    fun `less than packet works`() = Assertions.assertEquals(1, solve16_2("D8005AC2A8F0"))

    @Test
    fun `greater than packet works`() = Assertions.assertEquals(0, solve16_2("F600BC2D8F"))

    @Test
    fun `equal to packet works`() = Assertions.assertEquals(0, solve16_2("9C005AC2F8F0"))

    @Test
    fun `nested operations work`() = Assertions.assertEquals(1, solve16_2("9C0141080250320F1802104A08"))
}