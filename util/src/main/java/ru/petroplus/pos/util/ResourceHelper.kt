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

    fun getStringResource(resourceId: Int) = weakContext.get()?.let {
        it.resources.getString(resourceId)
    } ?: ""

    fun getAssetFile(certName: String): File? =
        weakContext.get()?.let {
            val inputStream = it.assets.open(certName)//("client_cert.pfx")
            return@let writeBytesToFile(inputStream)
        }

    fun getCaCertAssetContent(certName: String): String? =
        weakContext.get()?.let {
            val inputStream = it.assets.open(certName)
            return@let IOUtil.readInputStreamFully(inputStream)
        }

    fun getPingBinFile(certName: String): String? =
        weakContext.get()?.let {
            val inputStream = it.assets.open(certName)
            return@let IOUtil.readInputStreamFully(inputStream)
        }


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