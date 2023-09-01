package ru.petrolplus.pos.p7LibApi.responces

/**
 * @property sw1 - младший байт кода успешности выполнения
 * @property sw2 -старший байт кода успешности выполнения
 * @property data - данные ответа (необязательны)
 */
class ApduAnswer(
    var sw1: Int = 0,
    var sw2: Int = 0,
    var data: ByteArray = byteArrayOf()
)