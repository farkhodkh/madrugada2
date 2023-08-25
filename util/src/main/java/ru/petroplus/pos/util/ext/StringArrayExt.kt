package ru.petroplus.pos.util.ext

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

fun Array<String>.lengthOfSymbols(): Int {
    var length = 0
    this.forEach { length += it.length }
    return length
}