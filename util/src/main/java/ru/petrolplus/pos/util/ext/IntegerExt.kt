package ru.petrolplus.pos.util.ext

fun Int.getNextCommandNumber(): String = Integer.toHexString(this).padStart(2, '0')