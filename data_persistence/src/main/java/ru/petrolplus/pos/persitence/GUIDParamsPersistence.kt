package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.GUIDParamsDao
import ru.petrolplus.pos.room.entities.GUIDParamsDB
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для доступа к параметрам GUID (небходимые параметры для генерации GUID транзакции)
 */
interface GUIDParamsPersistence {

    /**
     * Получение GUID параметров терминала
     * @return GUID параметры терминала
     */
    suspend fun getGUIDparams(): GUIDParamsDTO

    /**
     * сохранение GUID параметров терминала
     * @param guidParams GUID параметры терминала
     */
    suspend fun setGUIDparams(guidParams: GUIDParamsDTO)
}

class GUIDParamsPersistenceImpl(
    private val guiDparamsDao: GUIDParamsDao,
    private val mapper: Mapper<GUIDParamsDTO, GUIDParamsDB>,
    private val persistenceStoreStrategy: StoreStrategy,
) : GUIDParamsPersistence {

    /**
     * Получает первую запись из хранилища
     * @throws IllegalStateException в случае если отсутствует запись в хранилище
     * @return DTO описывающую GUID параметры
     */
    @Throws(IllegalStateException::class)
    override suspend fun getGUIDparams(): GUIDParamsDTO {
        return guiDparamsDao.getById(1)?.let(mapper::toDTO) ?: throw NoRecordsException
    }

    /**
     * Вставляет или заменяет первую запись в таблице, маппер
     * @param guidParams DTO описывающая GUID параметры, должна иметь идентификатор равный 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun setGUIDparams(guidParams: GUIDParamsDTO) {
        persistenceStoreStrategy.store(guiDparamsDao, mapper, guidParams)
    }
}
