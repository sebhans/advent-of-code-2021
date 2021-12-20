package day20

import splitByEmptyLines
import trimmedLines

fun solve20_1(input: String) = input.splitByEmptyLines.let {
    val algorithm = ImageEnhancementAlgorithm.fromString(it[0])
    Image.fromString(it[1])
        .applyAlgorithm(algorithm, 2)
        .countLitPixels()
}

fun solve20_2(input: String) = input.splitByEmptyLines.let {
    val algorithm = ImageEnhancementAlgorithm.fromString(it[0])
    Image.fromString(it[1])
        .applyAlgorithm(algorithm, 50)
        .countLitPixels()
}

private fun Char.toPixelValue() = if (this == '#') 1 else 0

data class ImageEnhancementAlgorithm(private val pixelMap: List<Int>) {
    companion object {
        fun fromString(s: String) = ImageEnhancementAlgorithm(s.map(Char::toPixelValue))
    }

    fun enhance(
        topLeft: Int, top: Int, topRight: Int,
        left: Int, center: Int, right: Int,
        bottomLeft: Int, bottom: Int, bottomRight: Int
    ) = enhance((topLeft shl 8) + (top shl 7) + (topRight shl 6) +
                (left shl 5) + (center shl 4) + (right shl 3) +
                (bottomLeft shl 2) + (bottom shl 1) + bottomRight)

    fun enhance(pixelSum: Int) = pixelMap[pixelSum]
}

data class Image(private val pixels: List<List<Int>>, private val defaultPixel: Int) {
    companion object {
        fun fromString(s: String) = s.trimmedLines.map { line -> line.map(Char::toPixelValue) }.let { Image(it, 0) }
    }

    fun at(x: Int, y: Int) =
        if (x >= 0 && x < pixels[0].size && y >= 0 && y < pixels.size) pixels[y][x] else defaultPixel

    fun applyAlgorithm(algorithm: ImageEnhancementAlgorithm) =
        Image(
            buildList {
                for (y in -1..pixels.size) {
                    add(buildList {
                        for (x in -1..pixels[0].size) {
                            add(algorithm.enhance(at(x - 1, y - 1), at(x, y - 1), at(x + 1, y - 1),
                                    at(x - 1, y), at(x, y), at(x + 1, y),
                                    at(x - 1, y + 1), at(x, y + 1), at(x + 1, y + 1)))
                        }
                    })
                }
            },
            if (defaultPixel == 0) algorithm.enhance(0) else algorithm.enhance(511)
        )

    fun applyAlgorithm(algorithm: ImageEnhancementAlgorithm, times: Int) =
        generateSequence(this) { it.applyAlgorithm(algorithm) }
            .take(times + 1)
            .last()

    fun countLitPixels() = pixels.map(List<Int>::sum).sum()
}