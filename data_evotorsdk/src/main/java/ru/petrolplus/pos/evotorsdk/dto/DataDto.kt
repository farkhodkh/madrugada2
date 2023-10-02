package ru.petrolplus.pos.evotorsdk.dto

import ru.petrolplus.pos.evotorsdk.util.TlvCommands

/**
 * Класс с содержанием  данных после парсинга ответа от Эвотор
 * @param code - Команда TLV команды – копируется из запроса
 * @param rc - Код завершения
 * @param dataArray - Массив байтов с ответом, без выравнивания
 */
class DataDto(
    val code: TlvCommands?,
    val rc: Int,
    val dataArray: ByteArray,
)
