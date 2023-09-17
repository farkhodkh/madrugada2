package ru.petrolplus.pos.sdkapi.dto

import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Класс возвращается при успешном результате чтении карты
 */
class GetCardReaderInfoDto(
    override val data: BerTlvs,
    override val dataString: String = "Данные карты успешно прочитаны"
): TerminalDataDto