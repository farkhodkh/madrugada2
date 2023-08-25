package ru.petroplus.pos.p7LibApi.requests

/**
 * Класс задаёт структуру информационного сообщения, адресованного карте
 * @property GLA - класс команды, зависит от фазы жизни карты
 * @property INS - номер команды
 * @property P1 - первый параметр команды
 * @property P2 - второй параметр команды
 * @property LC - длинна данных команды
 * @property Data - данные команды
 * @property LE - длинна данных предполагаемого ответа
 */

class ApduData(
    var GLA: Byte = 0,
    var INS: Byte = 0,
    var P1: Byte = 0,
    var P2: Byte = 0,
    var LC: Byte = 0,
    var Data: ByteArray = byteArrayOf(),
    var LE: Byte = 0
)