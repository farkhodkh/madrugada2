package ru.petroplus.pos.util.ext

fun Number.leadingZeros(zerosInReceipt: Int): String = this.toString().padStart(zerosInReceipt, '0')
