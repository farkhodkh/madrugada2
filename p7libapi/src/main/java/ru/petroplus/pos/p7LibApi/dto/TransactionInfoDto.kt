package ru.petroplus.pos.p7LibApi.dto

/**
 * TODO -  Юрий добавь описание
 * @property transNumber - номер последней транзауции
 * @property cardNumber - номер карты
 * @property terminalNumber - номер терминала
 * @property terminalId - ID терминала
 * @property issuerId - ID эмитента
 */
class TransactionInfoDto(
    var transNumber:    UInt = 0u,
    var cardNumber:     UInt = 0u,
    var terminalNumber: UInt = 0u,
    var terminalId:     UInt = 0u,
    var issuerId:       UInt = 0u
)