package ru.petrolplus.pos.p7LibApi.requests

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

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class ApduData(
    var GLA: Byte = 0,
    var INS: Byte = 0,
    var P1: Byte = 0,
    var P2: Byte = 0,
    var LC: Byte = 0,
    var Data: ByteArray = byteArrayOf(),
    var LE: Byte = 0
)

/**
 * Метод возвращает массив APDU для Эвотор.
 * Порядок элементов массива:
 * CLA INS P1 P2 Lc <DataCmd> Le – для ICC и NFC интерфейсов
 */
fun ApduData.toEvotorApduByteArray(): ByteArray = byteArrayOf(this.GLA, this.INS, this.P1, this.P2)
    //TODO - В LC должна записываться длина массива "DATA", вычисляем длину самостоятельно
    // Уточнить почему LC не вычисляется на стороне библиотеки
    // Это только для select app или для всех apdu?
    .plus(this.Data.size.toByte())
    .plus(this.Data)