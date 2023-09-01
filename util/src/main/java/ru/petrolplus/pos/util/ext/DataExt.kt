package ru.petrolplus.pos.util.ext

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Переобразование Date в строку типа 11/07/23 23:32:43
 */
fun Date.toTextDate(): String {
    return SimpleDateFormat("dd/MM/yy hh:mm:ss", Locale.getDefault()).format(this)
}