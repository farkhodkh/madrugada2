package ru.petrolplus.pos.evotorsdk.util

//TODO - Рефактор этого класса необходим с учетом такого же файла в пакете tlv
object HexUtil {

    private val HEX_FORMAT = "%02X"

    fun toHexString(b: Byte): String = String.format(HEX_FORMAT, b)

    fun toHexString(data: ByteArray): String = toHexString(data, 0, data.size)

    fun toHexString(data: ByteArray, start: Int, stop: Int): String {
        val builder = StringBuilder()
        for (i in start until stop) {
            builder.append(toHexString(data[i]))
        }
        return builder.toString()
    }

    fun hexStringToByteArray(s: String): ByteArray {
        val len = s.length
        val data = ByteArray(len / 2)
        var i = 0
        while (i < len) {
            data[i / 2] = ((s[i].digitToIntOrNull(16) ?: (-1 shl 4)) + s[i + 1].digitToIntOrNull(16)!!).toByte()
            i += 2
        }
        return data
    }

    fun decodeHex(data: CharArray): ByteArray {
        val length = data.size
        val out = ByteArray(length shr 1)

        // two characters form the hex value.
        var index = 0
        var j = 0
        while (j < length) {
            var f = toDigit(data[j]) shl 4
            j++
            f = f or toDigit(data[j])
            j++
            out[index] = (f and 0xFF).toByte()
            index++
        }
        return out
    }

    private fun toDigit(ch: Char): Int = ch.digitToIntOrNull(16) ?: -1
}