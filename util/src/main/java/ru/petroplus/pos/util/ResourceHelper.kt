package ru.petroplus.pos.util

import android.annotation.SuppressLint
import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.ref.WeakReference
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

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

    fun getClientCertAssetFile(): File? =
        weakContext.get()?.let {
            val inputStream = it.assets.open("client_cert.pfx")
            return@let writeBytesToFile(inputStream)
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