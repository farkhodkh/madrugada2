package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Интерфейс для данных с результатами операций с терминалом
 */
interface TerminalDataDto {
    val data: BerTlvs?
    val dataString: String
}