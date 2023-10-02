package ru.petrolplus.pos.persitence.dto

/**
 * Содержит информацию о последнем номере онлайн-запроса и о параметрах, необходимых для генерации
 * GUID транзакции
 *
 * @param id идентификатор
 * @param lastOnlineTransaction число, используемое для формирования номера запроса для отправки на АС
 * @param lastGeneratedTime таймштамп генерации последнего GUID транзакции
 * @param clockSequence номер последовательности для генерации GUID
 * @param hasNodeId признак наличия NodeId
 * @param nodeId параметр для генерации GUID
 */

data class GUIDParamsDTO(
    override val id: Int = 1,
    val lastOnlineTransaction: Long,
    val lastGeneratedTime: Long,
    val clockSequence: Short,
    val hasNodeId: Boolean,
    val nodeId: String,
) : IdentifiableDTO
