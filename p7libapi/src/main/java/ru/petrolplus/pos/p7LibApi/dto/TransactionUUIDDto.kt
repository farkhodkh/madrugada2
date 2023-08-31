package ru.petrolplus.pos.p7LibApi.dto

/**
 * TODO - Юрий добавь описание
 * @property onlineTmNumber -
 * @property lastGenTime -
 * @property clockSequence -
 * @property hasNodeId -
 * @property nodeId -
 */
class TransactionUUIDDto(
    var onlineTmNumber: Int = 0,
    var lastGenTime: Long = 0L,
    var clockSequence: Int = 0,
    var hasNodeId: Boolean = false,
    var nodeId: String = ""
)