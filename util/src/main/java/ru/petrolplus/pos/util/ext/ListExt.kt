package ru.petrolplus.pos.util.ext

/**
 * Метод получает конкатенацию строк из входящего массива, без разделителя
 */
fun List<String>.toEvotorCommandLine(): String {
    val builder = StringBuilder()

    this.forEach {
        builder.append(it)
    }

    return builder.toString()
}
