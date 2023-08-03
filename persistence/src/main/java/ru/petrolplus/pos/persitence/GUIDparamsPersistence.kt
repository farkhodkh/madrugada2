package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.GUIDparamsDTO
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.GUIDparamsDao
import ru.petrolplus.pos.room.entities.GUIDparamsDB
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для доступа к параметрам GUID (небходимые параметры для генерации GUID транзакции)
 */
interface GUIDparamsPersistence {
    suspend fun getGUIDparams(): GUIDparamsDTO
    suspend fun setGUIDparams(guidParams: GUIDparamsDTO)
}

class GUIDparamsPersistenceImpl(
    private val guiDparamsDao: GUIDparamsDao,
    private val mapper: Mapper<GUIDparamsDTO, GUIDparamsDB>,
    private val persistenceStoreStrategy: StoreStrategy
) : GUIDparamsPersistence {

    /**
     * Получает первую запись из хранилища
     * @throws IllegalStateException в случае если отсутствует запись в хранилище
     * @return DTO описывающую GUID параметры
     */
    @Throws(IllegalStateException::class)
    override suspend fun getGUIDparams(): GUIDparamsDTO {
        return guiDparamsDao.getById(1)?.let(mapper::toDTO) ?: throw NoRecordsException
    }

    /**
     * Вставляет или заменяет первую запись в таблице, маппер
     * @param guidParams DTO описывающая GUID параметры, должна иметь идентификатор равный 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun setGUIDparams(guidParams: GUIDparamsDTO) {
        persistenceStoreStrategy.store(guiDparamsDao, mapper, guidParams)
    }

}