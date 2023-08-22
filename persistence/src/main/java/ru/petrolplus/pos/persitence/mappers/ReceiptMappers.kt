package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.dto.DebitReceiptDTO
import ru.petrolplus.pos.room.projections.ReceiptProjection

class DebitReceiptMapper : ProjectionMapper<ReceiptProjection, DebitReceiptDTO> {
    override fun fromProjection(input: ReceiptProjection): DebitReceiptDTO = DebitReceiptDTO(
        receiptNumber = input.receiptParamsDB.receiptNumber,
        organizationName = input.commonSettingsDB.organizationName,
        organizationInn = input.commonSettingsDB.organizationInn,
        posName = input.commonSettingsDB.posName,
        terminalDate = input.transactionDB.terminalDate,
        terminalId = input.transactionDB.terminalId,
        cardType = input.transactionDB.cardType,
        cardNumber = input.transactionDB.cardNumber,
        operationType = input.transactionDB.operationType,
        serviceName = input.serviceDB.name,
        serviceUnit = input.serviceDB.unit,
        amount = input.transactionDB.amount,
        sum = input.transactionDB.sum,
        price = input.transactionDB.price,
        responseCode = input.transactionDB.responseCode
    )
}