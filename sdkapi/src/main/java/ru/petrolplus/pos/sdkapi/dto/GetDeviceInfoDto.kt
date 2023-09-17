package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Класс возвращается послу успешного чтения данных устройства
 */
class GetDeviceInfoDto(
    override val data: BerTlvs?,
    override val dataString: String = ""
): TerminalDataDto