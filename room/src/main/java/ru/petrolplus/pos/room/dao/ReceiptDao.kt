package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.projections.ReceiptProjection

/**
 * Data Access Object для получения различных данных о чеках
 */
@Dao
interface ReceiptDao {

    /**
     * Комбинированный запрос из 4х таблиц (transactions, receipt_params, common_settings и services)
     * ищет по id определенную транзакцию ограничивает поиск только одним результатом. Создает фейковую колонку
     * со значением 1 для внешних связей через механизм OneToMany room см [ReceiptProjection]
     */
    @Query("SELECT *, 1 as relation_id FROM transactions WHERE id = :transactionId LIMIT 1")
    fun getDebitReceiptByTransactionId(transactionId: String): ReceiptProjection?
}