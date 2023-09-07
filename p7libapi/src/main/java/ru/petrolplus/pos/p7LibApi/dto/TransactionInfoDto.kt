package ru.petrolplus.pos.p7LibApi.dto

/**
 * @property transactionNumber - номер последней транзауции
 * @property cardNumber - номер карты
 * @property terminalNumber - номер терминала
 * @property terminalId - ID терминала
 * @property issuerId - ID эмитента
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class TransactionInfoDto(
    var transactionNumber: Long = 0L,
    var cardNumber: Long = 0L,
    var terminalNumber: Long = 0L,
    var terminalId: Long = 0L,
    var issuerId: Long = 0L
)