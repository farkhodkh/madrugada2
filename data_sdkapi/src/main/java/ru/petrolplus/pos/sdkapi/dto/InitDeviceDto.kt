package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Класс возвращается послу успешной инициализации устройства
 */
class InitDeviceDto(
    override val data: BerTlvs? = null,
    override val dataString: String = "Терминал успешно инициализирован",
) : TerminalDataDto
