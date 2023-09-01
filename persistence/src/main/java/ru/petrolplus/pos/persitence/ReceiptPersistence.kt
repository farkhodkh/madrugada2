package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.persitence.mappers.ProjectionMapper
import ru.petrolplus.pos.room.dao.ReceiptDao
import ru.petrolplus.pos.room.projections.ReceiptProjection

/**
 * Интерфейс для получения обобщенных данных для печати различных чеков(дебит, возврат и т.д)
 *
 */
interface ReceiptPersistence {

    /**
     * Получения чека дебета
     * @param transactionId идентификатор транзакции по которой нужно напечатать чек
     * @return DTO содержащую необходимые данные для печати чека дебета
     */
    suspend fun getDebitReceipt(transactionId: String): ReceiptDTO?
}

class ReceiptPersistenceImpl(
    private val receiptDao: ReceiptDao,
    private val mapper: ProjectionMapper<ReceiptProjection, ReceiptDTO>
) : ReceiptPersistence {

    override suspend fun getDebitReceipt(transactionId: String): ReceiptDTO? =
        receiptDao.getDebitReceiptByTransactionId(transactionId)?.let(mapper::fromProjection)


}