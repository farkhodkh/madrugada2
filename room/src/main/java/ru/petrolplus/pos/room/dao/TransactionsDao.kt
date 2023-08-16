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
     * Получение списка совершенных транзакций
     * @return коллекцию сущностей олицетворяющих совершенные транзакцию, может быть пустой
     * если совершенных транзакций нет
     */
    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDB>
}