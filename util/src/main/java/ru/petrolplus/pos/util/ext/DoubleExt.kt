package ru.petrolplus.pos.util.ext

fun Double.roundTo(decimals: Int): String {
    return String.format("%.${decimals}f", this)
}