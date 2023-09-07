package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.persitence.dto.ServiceTotalDTO
import ru.petrolplus.pos.room.projections.ReceiptProjection
import ru.petrolplus.pos.room.projections.TransactionByServiceProjection

class ReceiptMapper : ProjectionMapper<ReceiptProjection, ReceiptDTO> {
    override fun fromProjection(input: ReceiptProjection): ReceiptDTO = ReceiptDTO(
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
        responseCode = input.transactionDB.responseCode,
        operatorNumber = input.transactionDB.operatorNumber
    )
}

class ShiftReceiptMapper : ProjectionMapper<TransactionByServiceProjection, ServiceTotalDTO> {
    override fun fromProjection(input: TransactionByServiceProjection): ServiceTotalDTO =
        ServiceTotalDTO(
            serviceName = input.serviceDb.name,
            serviceUnit = input.serviceDb.unit,
            servicePrice = input.serviceDb.price,
            totalAmount = input.totalAmount,
            totalSum = input.totalSum,
            totalRecalculationAmount = input.totalRecalculationAmount,
            totalRecalculationSum = input.totalRecalculationSum
        )

}