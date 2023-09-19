package ru.petrolplus.pos.util

import android.content.Context
import ru.petrolplus.pos.util.constants.Constants
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface ErrorLogger {
    fun log(throwable: Throwable)
}

class FileLogger(private val context: Context) : ErrorLogger {

    private val locale = Locale(Constants.RUSSIAN)
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", locale)
    private val timeFormat = SimpleDateFormat("HH:mm:ss", locale)
    override fun log(throwable: Throwable) {
        val currentDateTime = Date()
        val errorMessage = "Exception time: ${timeFormat.format(currentDateTime)}" + "\n" + throwable.stackTraceToString()

        val logFileName = "Log_${dateFormat.format(currentDateTime)}"

        val logFile = File(context.externalCacheDir, "$logFileName.log")

        try {
            val writter = FileWriter(logFile, true)
            writter.append("\n")
            writter.append(errorMessage)
            writter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}