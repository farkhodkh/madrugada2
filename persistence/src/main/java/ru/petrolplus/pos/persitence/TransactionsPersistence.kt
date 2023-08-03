package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.entities.TransactionDB

/**
 * Интерфейс для доступа к транзакциям
 */
interface TransactionsPersistence {
    suspend fun getAll(): List<TransactionDTO>
    suspend fun add(transaction: TransactionDTO)
}

class TransactionsPersistenceImpl(
    private val transactionsDao: TransactionsDao,
    private val mapper: Mapper<TransactionDTO, TransactionDB>,
) : TransactionsPersistence {

    override suspend fun getAll(): List<TransactionDTO> {
        return transactionsDao.getAll().map(mapper::toDTO)
    }

    override suspend fun add(transaction: TransactionDTO) {
        transactionsDao.insert(mapper.fromDTO(transaction))
    }

}