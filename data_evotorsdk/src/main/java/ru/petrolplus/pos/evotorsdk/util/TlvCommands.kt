package ru.petrolplus.pos.evotorsdk.util

import java.math.BigInteger

/**
 * Содержит список TLV команд
 * Следующие команды не содержат выравнивания:
 * 0x00
 * 0x01
 * 0x02
 * 0x03
 * 0x20
 * 0x21
 * 0x23
 * Для других команд выравнивания присутствуют по логике
 * Если в команде есть данные, то по протоколу нужно добавлять к данным блок выравнивания.
 * Делаем так:
 * 1) Смотрим на команду:
 * 53 42 52 04 04 01 01 02 03 0D 00 A4 04 00 07 FF 00 00 B0 08 00 02 00
 * Всё, что после номера команды - это данные команды. Вот они:
 * 01 01 02 03 0D 00 A4 04 00 07 FF 00 00 B0 08 00 02 00
 * 2) Вычисляем длину: 18 байт
 * 3) Смотрим, сколько не хватает байт до ближайшего кратного восьми. Получается 24-18=6
 * 4) Добавляем 6 байт к команде в виде:
 * 00 00 00 00 00 06
 * 5) Теперь пакет выглядит так:
 * 53 42 52 04 04 01 01 02 03 0D 00 A4 04 00 07 FF 00 00 B0 08 00 02 00 00 00 00 00 00 06
 * P.S. Если длина блока данных получилась кратной восьми байтам,
 * всё равно выравниваем до следующей кратной восьми длины, то есть добавляем 00 00 00 00 00 00 00 08
 */
sealed class TlvCommands(val code: String, val codeInt: Int, val hasAlignment: Boolean = true) {
    /**
     * Request header
     */
    object RequestHeader : TlvCommands(String.format("%04x", BigInteger(1, "SBR".toByteArray(Charsets.UTF_8))), 1)

    /**
     * Response header
     */
    object ResponseHeader : TlvCommands(String.format("%04x", BigInteger(1, "SBA".toByteArray(Charsets.UTF_8))), 1)

    /**
     * Код для инициализации/сброса команд
     */
    object InitDevice : TlvCommands("00", 0, false)

    /**
     * Получить информацию об устройстве
     */
    object GetDeviceInfo : TlvCommands("01", 1, false)

    /**
     * Получить состояние кардридера
     */
    object GetCardReaderInfo : TlvCommands("02", 2, false)

    /**
     * Включить/выключить кардридер
     * Устанавливает маску используемых кардридеров.
     * Деактивируются все открытые считки карт. Включение поля и питания происходит при выполнении команды 02.
     */
    object InitCardReader : TlvCommands("03", 3, false)

    /**
     * Обмен APDU
     */
    object ExchangeWithAPDU : TlvCommands("04", 4)

    /**
     * Ввести ПИН без обращения к карте
     */
    object EnterOfflinePin : TlvCommands("25", 25)
    companion object {
        fun findById(code: Int): TlvCommands? =
            TlvCommands::class.nestedClasses
//                .filter { klass -> klass is TlvCommands }
                .map { klass -> klass.objectInstance }
                .filterIsInstance<TlvCommands>()
                .firstOrNull { value -> value.codeInt == code }
    }
}
