package ru.petroplus.pos.p7LibApi.dto

/**
 * TODO - Юрий добавь описание
 * @property onlineTransNumber -
 * @property lastGenTime -
 * @property clockSequence -
 * @property hasNodeId -
 * @property nodeId -
 */
class TransactionUUIDDto(
    var onlineTransNumber: Int = 0,
    var lastGenTime: Int = 0,
    var clockSequence: Int = 0,
    var hasNodeId: Boolean = false,
    var nodeId: ByteArray = byteArrayOf()
)