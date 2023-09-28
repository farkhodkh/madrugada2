package ru.petrolplus.pos.util

import android.content.Context
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface ErrorLogger {
    fun log(throwable: Throwable)
}

class FileLogger(private val context: Context) : ErrorLogger {

    private val locale = Locale.getDefault()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
    private val timeFormat = SimpleDateFormat("HH:mm:ss", locale)
    override fun log(throwable: Throwable) {
        val currentDateTime = Date()
        val errorMessage = "Exception time: ${timeFormat.format(currentDateTime)}" + "\n" + throwable.stackTraceToString()

        val logFileName = "Log_${dateFormat.format(currentDateTime)}"

        val logFile = File(context.externalCacheDir, "$logFileName.log")

        try {
            val writer = FileWriter(logFile, true)
            writer.append("\n")
            writer.append(errorMessage)
            writer.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}