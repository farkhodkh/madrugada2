package ru.petrolplus.pos.util.ext

fun String.isNotConfigurationCommentedLine() =
    this.startsWith(";") || this.startsWith("[")

/**
 * Метод проверяет корректность ATR
 * TODO - длину не проверяем, результаты теста покажут
 */
fun String?.isCorrectEvotorAtr(): Boolean =
    this != null && this.startsWith("3B") // && this.length == 22

/**
 * Метод возвращает строку с выравниванием команды Эвотор
 */
fun String.getEvotorAlignmentString(): String {
    val multiplicity = 8 - (this.length % 8)
    return "${"00".repeat(multiplicity - 1)}0$multiplicity"
}
