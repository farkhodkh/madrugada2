package ru.petrolplus.pos.printerapi.ext

import ru.petrolplus.pos.printerapi.Formatting.PRINTER_DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


private val printerDateFormatter by lazy { SimpleDateFormat(PRINTER_DATE_PATTERN, Locale.getDefault()) }
fun Date.formattingForPrinter(): String = printerDateFormatter.format(this) ?: ""
