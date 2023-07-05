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
    var GLA: Byte,
    var INS: Byte,
    var P1: Byte,
    var P2: Byte,
    var LC: Byte,
    var Data: ByteArray,
    var LE: Byte
)