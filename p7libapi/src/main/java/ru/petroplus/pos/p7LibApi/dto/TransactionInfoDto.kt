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
    var onlineTmNumber: Int = 0,
    var cardNumber: String = "",
    var terminalNumber: Int = 0,
    var terminalId: Int = 0,
    var issuerId: Int = 0
)