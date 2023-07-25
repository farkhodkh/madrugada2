package ru.petroplus.pos.p7LibApi.requests

/**
 * Класс задаёт структуру информационного сообщения, адресованного карте
 * TODO -  Юрий добавь описание
 * @property GLA -
 * @property INS -
 * @property P1 -
 * @property P2 -
 * @property LC -
 * @property Data -
 * @property LE -
 */
class ApduData(
    var GLA:  Byte = 0,
    var INS:  Byte = 0,
    var P1:   Byte = 0,
    var P2:   Byte = 0,
    var LC:   Byte = 0,
    var Data: ByteArray = byteArrayOf(),
    var LE:   Byte = 0
)