package day16

fun solve16_1(input: String) = input.toBitString().parseBITS().sumVersions()

fun solve16_2(input: String) = input.toBitString().parseBITS().value

private fun String.toBitString() = split("").filter { it.isNotBlank() }
    .joinToString(separator = "") { it.toInt(16).toString(2).padStart(4, '0') }

private fun String.parseBITS() = Packet.fromString(this)

private sealed class Packet(val version: Int, val length: Int) {
    companion object {
        fun fromString(s: String): Packet {
            val version = s.slice(0..2).toInt(2)
            return when (val typeID = s.slice(3..5).toInt(2)) {
                4 -> LiteralValuePacket.fromString(version, s.slice(6..s.lastIndex))
                else -> OperatorPacket.fromString(version, typeID, s.slice(6..s.lastIndex))
            }
        }
    }

    open fun sumVersions() = version

    abstract val value: Long
}

private class LiteralValuePacket(version: Int, length: Int, override val value: Long) : Packet(version, length) {
    companion object {
        fun fromString(version: Int, s: String): LiteralValuePacket {
            var i = 0
            var value = 0L
            while (s[i] == '1') {
                value = value * 16 + s.slice(i + 1..i + 4).toInt(2)
                i += 5
            }
            value = value * 16 + s.slice(i + 1..i + 4).toInt(2)
            return LiteralValuePacket(version, 6 /* header */ + i + 5 /* last group */, value)
        }
    }
}

private class OperatorPacket(version: Int, length: Int, val operation: Operation, val children: List<Packet>) :
    Packet(version, length) {
    companion object {
        fun fromString(version: Int, typeID: Int, s: String): OperatorPacket {
            return if (s[0] == '0') {
                s.slice(1..15).toInt(2).let { childrenLength ->
                    parseChildrenByTotalLength(s.slice(16..(15 + childrenLength))).let {
                        OperatorPacket(version, 6 /* header */ + 1 /* length type ID */ + 15 /* total length field */ + childrenLength, Operation.byTypeID(typeID), it)
                    }
                }
            } else {
                s.slice(1..11).toInt(2).let { numChildren ->
                    parseChildrenByNumber(s.slice(12..s.lastIndex), numChildren).let {
                        OperatorPacket(version, 6 /* header */ + 1 /* length type ID */ + 11 /* num children field */ + it.sumOf(Packet::length), Operation.byTypeID(typeID), it)
                    }
                }
            }
        }

        private fun parseChildrenByTotalLength(_s: String): List<Packet> {
            var s = _s
            val children = mutableListOf<Packet>()
            while (s.isNotEmpty()) {
                children.add(fromString(s))
                s = s.slice(children.last().length..s.lastIndex)
            }
            return children
        }

        private fun parseChildrenByNumber(_s: String, n: Int): List<Packet> {
            var s = _s
            val children = mutableListOf<Packet>()
            repeat(n) {
                children.add(fromString(s))
                s = s.slice(children.last().length..s.lastIndex)
            }
            return children
        }
    }

    override fun sumVersions() = children.fold(version) { sum, child -> sum + child.sumVersions() }

    override val value: Long get() = operation.applyTo(children)
}

private fun interface Operation {
    companion object {
        fun byTypeID(typeID: Int) = when (typeID) {
            0 -> Operation { packets -> packets.sumOf(Packet::value) }
            1 -> Operation { packets -> packets.productOf(Packet::value) }
            2 -> Operation { packets -> packets.minOf(Packet::value) }
            3 -> Operation { packets -> packets.maxOf(Packet::value) }
            5 -> Operation { packets -> if (packets[0].value > packets[1].value) 1 else 0 }
            6 -> Operation { packets -> if (packets[0].value < packets[1].value) 1 else 0 }
            7 -> Operation { packets -> if (packets[0].value == packets[1].value) 1 else 0 }
            else -> throw IllegalArgumentException("unsupported packet type: $typeID")
        }
    }

    fun applyTo(packets: List<Packet>): Long
}

private inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var product = 1L
    for (element in this) {
        product *= selector(element)
    }
    return product
}
