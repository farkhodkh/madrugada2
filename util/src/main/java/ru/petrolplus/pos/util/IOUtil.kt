package ru.petrolplus.pos.util

import java.io.BufferedInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream

object IOUtil {
    fun readInputStreamFully(inputStream: InputStream?): String {
        if (inputStream == null) {
            return ""
        }

        var bufferedInputStream: BufferedInputStream? = null

        try {
            bufferedInputStream = BufferedInputStream(inputStream)
            val byteArrayOutputStream = ByteArrayOutputStream()
            val buffer = ByteArray(1024)
            var available = bufferedInputStream.read(buffer)

            while (available >= 0) {
                byteArrayOutputStream.write(buffer, 0, available)
                available = bufferedInputStream.read(buffer)
            }

            return byteArrayOutputStream.toString()

        } finally {
            bufferedInputStream?.close()
        }
    }
}