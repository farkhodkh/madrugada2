package ru.petrolplus.pos.p7LibApi.responces

/**
 * @property sw1 - младший байт кода успешности выполнения
 * @property sw2 -старший байт кода успешности выполнения
 * @property data - данные ответа (необязательны)
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class ApduAnswer(
    var sw1: Int = 0,
    var sw2: Int = 0,
    var data: ByteArray = byteArrayOf()
)