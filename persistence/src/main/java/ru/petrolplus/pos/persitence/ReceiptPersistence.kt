package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.DebitReceiptDTO
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
    suspend fun getDebitReceipt(transactionId: String): DebitReceiptDTO?
}

class ReceiptPersistenceImpl(
    private val receiptDao: ReceiptDao,
    private val mapper: ProjectionMapper<ReceiptProjection, DebitReceiptDTO>
) : ReceiptPersistence {

    override suspend fun getDebitReceipt(transactionId: String): DebitReceiptDTO? {
        return receiptDao.getDebitReceiptByTransactionId(transactionId)?.let(mapper::fromProjection)
    }


}