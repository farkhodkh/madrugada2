package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.entities.TransactionDB

/**
 * Интерфейс для доступа к совершенным транзакциям
 */
interface TransactionsPersistence {

    /**
     * метод для получения списка транзакций
     * @return список совершенных транзакций
     */
    suspend fun getAll(): List<TransactionDTO>

    /**
     * метод для добавление транзакции в хранилище
     * @param transaction добавляемая транзакция
     */
    suspend fun add(transaction: TransactionDTO)

    /**
     * метод для получение транзакции по идентификатору
     * @param transactionId идентификатор транзакции
     * @return возвращает DTO транзакции, может быть null если не найдено ни одной подходящей
     */
    suspend fun getById(transactionId: String): TransactionDTO?
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

    override suspend fun getById(transactionId: String): TransactionDTO? {
        return transactionsDao.getById(transactionId)?.let(mapper::toDTO)
    }
}
