package ru.petroplus.pos.p7LibApi.responces

/**
 * TODO - Юрий, добавть описание
 * @property sw1 -
 * @property sw2 -
 * @property data -
 */
class ApduAnswer(
    var sw1: Int = 0,
    var sw2: Int = 0,
    var data: ByteArray = byteArrayOf()
)