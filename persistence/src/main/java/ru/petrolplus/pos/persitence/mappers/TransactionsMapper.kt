package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petrolplus.pos.room.entities.TransactionDB

class TransactionsMapper : Mapper<TransactionDTO, TransactionDB> {
    override fun toDTO(input: TransactionDB): TransactionDTO = TransactionDTO(
        id = input.id,
        acquirerId = input.acquirerId,
        terminalId = input.terminalId,
        cardNumber = input.cardNumber,
        operatorNumber = input.operatorNumber,
        terminalDate = input.terminalDate,
        serviceIdWhat = input.serviceIdWhat,
        serviceIdFrom = input.serviceIdFrom,
        amount = input.amount,
        price = input.price,
        sum = input.sum,
        cardTransactionCounter = input.cardTransactionCounter,
        hasReturn = input.hasReturn,
        crs32 = input.crs32,
        operationType = input.operationType,
        cardType = input.cardType,
        loyaltySum = input.loyaltySum,
        deltaBonus = input.deltaBonus,
        originalDebitTransactionId = input.originalDebitTransactionId,
        shiftNumber = input.shiftNumber,
        hasRecalculationTransaction = input.hasRecalculationTransaction,
        rollbackCode = input.rollbackCode,
        receiptNumber = input.receiptNumber,
        responseCode = input.responseCode
    )

    override fun fromDTO(input: TransactionDTO): TransactionDB = TransactionDB(
        id = input.id,
        acquirerId = input.acquirerId,
        terminalId = input.terminalId,
        cardNumber = input.cardNumber,
        operatorNumber = input.operatorNumber,
        terminalDate = input.terminalDate,
        serviceIdWhat = input.serviceIdWhat,
        serviceIdFrom = input.serviceIdFrom,
        amount = input.amount,
        price = input.price,
        sum = input.sum,
        cardTransactionCounter = input.cardTransactionCounter,
        hasReturn = input.hasReturn,
        crs32 = input.crs32,
        operationType = input.operationType,
        cardType = input.cardType,
        loyaltySum = input.loyaltySum,
        deltaBonus = input.deltaBonus,
        originalDebitTransactionId = input.originalDebitTransactionId,
        shiftNumber = input.shiftNumber,
        hasRecalculationTransaction = input.hasRecalculationTransaction,
        rollbackCode = input.rollbackCode,
        receiptNumber = input.receiptNumber,
        responseCode = input.responseCode
    )

}