package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Класс возвращается после успешной инициализации карт ридера
 */
class InitCardReaderDto(
    override val data: BerTlvs? = null,
    override val dataString: String = "Card reader успешно инициализирован",
) : TerminalDataDto
