package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Класс для переброски данных APDU
 */
class CommonStateDto(
    override val data: BerTlvs?,
    override val dataString: String
): TerminalDataDto