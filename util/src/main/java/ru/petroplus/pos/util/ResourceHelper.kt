package ru.petroplus.pos.util

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.ref.WeakReference

object ResourceHelper {
    private lateinit var weakContext: WeakReference<Context>

    fun getContext() : Context  = weakContext.get()!!

    /**
     * Установка контекста для Helper
     */
    fun setContext(context: Context) {
        weakContext = WeakReference(context)
    }

    /**
     * Метод для получение строки из ресурсов
     */
    fun getStringResource(resourceId: Int) = weakContext.get()?.let {
        it.resources.getString(resourceId)
    } ?: ""

    /**
     * Метод для получения файлов из assets
     */
    fun getAssetFile(fileName: String): File? =
        weakContext.get()?.let {
            val inputStream = it.assets.open(fileName)
            return@let writeBytesToFile(inputStream)
        }

    /**
     * Метод для чтения содержимого файла из assets
     */
    fun readAssetContent(fileName: String): String? =
        weakContext.get()?.let {
            val inputStream = it.assets.open(fileName)
            return@let IOUtil.readInputStreamFully(inputStream)
        }

    /**
     * Метод для записи InputStream в файл для Ping запроса
     */
    private fun writeBytesToFile(inputStream: InputStream): File {
        val file = File.createTempFile("PingP7", ".bin")
        var data = ByteArray(inputStream.available())
        val bread = 0
        val fos = FileOutputStream(file)

        while (inputStream.read(data) > bread) {
            fos.write(data)
        }

        return file
    }
}