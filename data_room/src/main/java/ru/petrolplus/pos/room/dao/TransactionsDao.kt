package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.TransactionDB

/**
 * Data Access Object для получения и сохранения транзакций
 * название таблицы и полей можно посмотреть в [TransactionDB]
 */
@Dao
interface TransactionsDao : BaseDao<TransactionDB> {

    /**
     * Метод для получения транзакции по ее идентификатору
     * @param id идентификатор транзакции
     * @return сущность олицетворяющую совершенную транзакцию, может быть null если не удалось найти
     * транзакцию
     */
    @Query("SELECT * FROM transactions WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): TransactionDB?

    /**
     * Метод для получения последней транзакции по определенном типу, номеру карты и типу услуги
     * @param cardNumber см [TransactionDB.cardNumber]
     * @param serviceId cм [TransactionDB.serviceIdWhat]
     * @param operationType см [TransactionDB.operationType] по умолчанию 1 - дебет.
     */
    @Query("SELECT * FROM transactions " +
            "WHERE card_number = :cardNumber " +
            "AND service_id_what = :serviceId " +
            "AND operation_type = :operationType " +
            "ORDER BY datetime(terminal_date) " +
            "DESC LIMIT 1"
    )
    suspend fun getLastByCardNumberAndService(cardNumber: String, serviceId: Int, operationType: Int = 1): TransactionDB?

    /* @Transaction
     @Query(
         "SELECT cs.*, t.* FROM transactions t " +
                 "INNER JOIN common_settings cs ON cs.id = 1 " +
                 "WHERE t.id = :transactionId"
     )
     suspend fun getGeneralById(transactionId: String): TransactionEmbeddedDB?*/

    /**
     * Получение списка совершенных транзакций
     * @return коллекцию сущностей олицетворяющих совершенные транзакцию, может быть пустой
     * если совершенных транзакций нет
     */
    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDB>
}