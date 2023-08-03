package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.TransactionDB

@Dao
interface TransactionsDao : BaseDao<TransactionDB> {

    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionDB>
}