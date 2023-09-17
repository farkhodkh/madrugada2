package ru.petrolplus.pos.util.ext

/**
 * Расширение для получение номера команды с ведущим нулем
 */
fun Int.getNextCommandNumber(): String = Integer.toHexString(this).padStart(2, '0')
