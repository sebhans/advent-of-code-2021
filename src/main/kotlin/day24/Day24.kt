package day24

fun solve24_1(input: String): Long {
    val n = 41299994879959
    if (monadSimplified(n.toString()) == 0) return n
    else throw IllegalArgumentException("program rejects valid model number")
}

fun solve24_2(input: String): Long {
    val n = 11189561113216
    if (monadSimplified(n.toString()) == 0) return n
    else throw IllegalArgumentException("program rejects valid model number")
}

fun monadSimplified(input: String): Int {
    val inp = input.map { it - '0' }
    var w = 0
    var x = 0
    var z = 0

    w = inp[0]
    z = w + 16

    w = inp[1]
    z *= 26
    z += w + 11

    w = inp[2]
    z *= 26
    z += w + 12

    w = inp[3]
    x = if (z % 26 - 5 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 12) * x

    w = inp[4]
    x = if (z % 26 - 3 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 12) * x

    w = inp[5]
    x = if (z % 26 + 14 != w) 1 else 0
    z *= 25 * x + 1
    z += (w + 2) * x

    w = inp[6]
    x = if (z% 26 + 15 != w) 1 else 0
    z *= 25 * x + 1
    z += (w + 11) * x

    w = inp[7]
    x = if (z % 26 - 16 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 4) * x

    w = inp[8]
    x = if (z % 26 + 14 != w) 1 else 0
    z *= 25 * x + 1
    z += (w + 12) * x

    w = inp[9]
    x = if (z % 26 + 15 != w) 1 else 0
    z *= 25 * x + 1
    z += (w + 9) * x

    w = inp[10]
    x = if (z % 26 - 7 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 10) * x

    w = inp[11]
    x = if (z % 26 - 11 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 11) * x

    w = inp[12]
    x = if (z % 26 - 6 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 6) * x

    w = inp[13]
    x = if (z % 26 - 11 != w) 1 else 0
    z /= 26
    z *= 25 * x + 1
    z += (w + 15) * x

    return z
}