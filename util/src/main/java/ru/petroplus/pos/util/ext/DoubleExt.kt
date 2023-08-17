package ru.petroplus.pos.util.ext

fun Double.roundTo(decimals: Int): String {
    return String.format("%.${decimals}", this)
}