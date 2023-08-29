package ru.petroplus.pos.evotorsdk.util

import java.math.BigInteger

/**
 * Содержит список TLV команд
 */
sealed class TlvCommands(val code: String) {
    /**
     * Request header
     */
    object RequestHeader: TlvCommands(String.format("%04x", BigInteger(1, "SBR".toByteArray(Charsets.UTF_8))))

    /**
     * Response header
     */
    object ResponseHeader: TlvCommands(String.format("%04x", BigInteger(1, "SBA".toByteArray(Charsets.UTF_8))))

    /**
     * Код для инициализации/сброса команд
     */
    object InitDevice: TlvCommands("00")

    /**
     * Получить информацию об устройстве
     */
    object GetDeviceInfo: TlvCommands("01")

    /**
     * Получить состояние кардридера
     */
    object GetCardReaderInfo: TlvCommands("02")


    /**
     * Включить/выключить кардридер
     * Устанавливает маску используемых кардридеров.
     * Деактивируются все открытые считки карт. Включение поля и питания происходит при выполнении команды 02.
     */
    object InitCardReader: TlvCommands("03")


    /**
     * Обмен APDU
     */
    object ExchangeWithAPDU: TlvCommands("04")

    /**
     * Ввести ПИН без обращения к карте
     */
    object EnterOfflinePin: TlvCommands("25")
}