package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.persitence.dto.ServiceTotalDTO
import ru.petrolplus.pos.persitence.dto.ShiftReceiptDTO
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.ProjectionMapper
import ru.petrolplus.pos.room.dao.ReceiptDao
import ru.petrolplus.pos.room.projections.ReceiptProjection
import ru.petrolplus.pos.room.projections.TransactionByServiceProjection
import kotlin.jvm.Throws

/**
 * Интерфейс для получения обобщенных данных для печати различных чеков(дебит, возврат и т.д)
 *
 */
interface ReceiptPersistence {

    /**
     * Получение чека дебета
     * @param transactionId идентификатор транзакции по которой нужно напечатать чек
     * @return DTO содержащую необходимые данные для печати чека дебета
     */
    suspend fun getDebitReceipt(transactionId: String): ReceiptDTO?

    /**
     * Получение чека отчета по текущей смене
     * содержит номер смены, параметры организации, а также все транзакции по данной смене
     * @return DTO содержащую необходимые данные для печати сменного отчета
     */
    suspend fun getShiftReceipt(): ShiftReceiptDTO
}

class ReceiptPersistenceImpl(
    private val receiptDao: ReceiptDao,
    private val debitReceiptMapper: ProjectionMapper<ReceiptProjection, ReceiptDTO>,
    private val serviceTotalMapper: ProjectionMapper<TransactionByServiceProjection, ServiceTotalDTO>,
) : ReceiptPersistence {

    override suspend fun getDebitReceipt(transactionId: String): ReceiptDTO? =
        receiptDao.getDebitReceiptByTransactionId(transactionId)?.let(debitReceiptMapper::fromProjection)

    @Throws(NoRecordsException::class)
    override suspend fun getShiftReceipt(): ShiftReceiptDTO {
        val shiftInfo = try {
            receiptDao.getShiftInfo()
        } catch (e: NullPointerException) { throw NoRecordsException }

        val shiftNumber = shiftInfo.shiftParamsDB.currentShiftNumber
        val debits = receiptDao.getReportByOperationTypeForShift(OperationType.DEBIT.id, shiftNumber)
        val cardRefunds = receiptDao.getReportByOperationTypeForShift(OperationType.CARD_REFUND.id, shiftNumber)
        val accountRefunds = receiptDao.getReportByOperationTypeForShift(OperationType.ACCOUNT_REFUND.id, shiftNumber)
        val cardsTotal = receiptDao.getCardsTotal(shiftNumber)

        return ShiftReceiptDTO(
            shiftNumber = shiftNumber,
            currentShiftStart = shiftInfo.shiftParamsDB.currentShiftStartsTimestamp,
            organizationName = shiftInfo.commonSettingsDB.organizationName,
            organizationInn = shiftInfo.commonSettingsDB.organizationInn,
            posName = shiftInfo.commonSettingsDB.posName,
            terminalId = shiftInfo.terminalId,
            debits = debits.map(serviceTotalMapper::fromProjection),
            cardRefunds = cardRefunds.map(serviceTotalMapper::fromProjection),
            accountRefunds = accountRefunds.map(serviceTotalMapper::fromProjection),
            operatorNumb = shiftInfo.operatorNumber,
            totalDebitsOperations = cardsTotal.totalDebits,
            totalRefundsOperations = cardsTotal.totalRefunds,
            totalOperations = cardsTotal.totalOperations,
            totalSum = cardsTotal.totalSum,
        )
    }
}
