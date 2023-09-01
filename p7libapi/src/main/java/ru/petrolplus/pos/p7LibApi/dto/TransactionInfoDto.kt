package ru.petrolplus.pos.p7LibApi.dto

/**
 * @property transNumber - номер последней транзауции
 * @property cardNumber - номер карты
 * @property terminalNumber - номер терминала
 * @property terminalId - ID терминала
 * @property issuerId - ID эмитента
 */
class TransactionInfoDto(
    var transactionNumber: Long = 0L,
    var cardNumber: Long = 0L,
    var terminalNumber: Long = 0L,
    var terminalId: Long = 0L,
    var issuerId: Long = 0L
)