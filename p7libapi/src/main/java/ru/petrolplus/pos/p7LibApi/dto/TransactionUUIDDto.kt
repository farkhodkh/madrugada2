package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс для задания уникальной информации о отранзакции
 * @property onlineTransNumber - номер транзакции
 * @property lastGenTime - время транзакции (младшие байты)
 * @property clockSequence - количество тиков с начала эпохи на момент выполнения транзакции (младшие байты)
 * @property hasNodeId - признак наличия идентивикатора узла
 * @property nodeId - код идентификатора узла
 */
class TransactionUUIDDto(
    var onlineTransNumber: Int = 0,
    var lastGenTime: Int = 0,
    var clockSequence: Int = 0,
    var hasNodeId: Boolean = false,
    var nodeId: ByteArray = byteArrayOf()
)