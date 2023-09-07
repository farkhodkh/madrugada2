package ru.petrolplus.pos.util.ext

fun Array<String>.justify(sizeLine: Int): String {
    val spaceOccupied: Int = this.lengthOfSymbols()
    val sizeOfSpace = if (spaceOccupied < sizeLine) sizeLine - spaceOccupied else 0
    val space = " ".repeat(sizeOfSpace)

    val joined = StringBuilder()
    this.forEachIndexed { idx, s ->
        if (idx != 0) joined.append(space)
        joined.append(s)
    }

    return joined.toString()
}

fun Array<String>.justifyWithCenterMiddle(sizeLine: Int, offset: Int
): String {
    val left: String = this[0]
    val middle = this[1]
    val right = this[2]

    val freeSpace = (sizeLine - middle.length) / 2
    val leftSpaceSize = freeSpace - left.length - offset
    val leftSpace = " ".repeat(leftSpaceSize)

    val rightSpaceSize = sizeLine - leftSpaceSize - left.length - middle.length - right.length
    val rightSpace = " ".repeat(if (rightSpaceSize > 0) rightSpaceSize else 0)

    return "$left$leftSpace$middle$rightSpace$right"
}

fun Array<String>.lengthOfSymbols(): Int {
    var length = 0
    this.forEach { length += it.length }
    return length
}