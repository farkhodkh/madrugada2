package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.ReceiptParamsDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.ReceiptParamsDao
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для доступа к данным о последнем напечатанном чеке
 */
interface ReceiptParamsPersistence {

    /**
     * Сохранение общих настроек терминала
     * @param receiptParams общие настройки терминала
     */
    suspend fun setReceiptParams(receiptParams: ReceiptParamsDTO)
}

class ReceiptParamsPersistenceImpl(
    private val receiptParamsDao: ReceiptParamsDao,
    private val mapper: Mapper<ReceiptParamsDTO, ReceiptParamsDB>,
    private val persistenceStoreStrategy: StoreStrategy
) : ReceiptParamsPersistence {


    /**
     * Вставляет или заменяет первую запись в таблице
     * @param receiptParams DTO описывающие параметры последнего напечатанного чека, идентификатор должен равняться 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun setReceiptParams(receiptParams: ReceiptParamsDTO) {
        persistenceStoreStrategy.store(receiptParamsDao, mapper, receiptParams)
    }

}