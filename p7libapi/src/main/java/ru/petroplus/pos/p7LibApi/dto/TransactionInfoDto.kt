package ru.petroplus.pos.p7LibApi.dto

/**
 * TODO Нужны описание класса и свойств
 * @property onlineTmNumber -
 * @property cardNumber -
 * @property terminalNumber -
 * @property terminalId -
 * @property issuerId -
 */
class TransactionInfoDto(
    var onlineTmNumber: Int,
    var cardNumber: String,
    var terminalNumber: Int,
    var terminalId: Int,
    var issuerId: Int
)