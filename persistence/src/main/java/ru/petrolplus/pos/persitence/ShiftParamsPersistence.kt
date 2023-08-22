package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.ShiftParamsDTO
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для доступа к параметрам смены
 */
interface ShiftParamsPersistence {

    /**
     * Получение парметров смены
     * @return параметры смены
     */
    suspend fun getShiftSettings(): ShiftParamsDTO

    /**
     * Сохранение парметров смены
     * @param shiftParams параметры смены
     */
    suspend fun setShiftParams(shiftParams: ShiftParamsDTO)
}

class ShiftParamsPersistenceImpl(
    private val shiftParamsDao: ShiftParamsDao,
    private val mapper: Mapper<ShiftParamsDTO, ShiftParamsDB>,
    private val persistenceStoreStrategy: StoreStrategy
) : ShiftParamsPersistence {

    /**
     * Получает первую запись из хранилища
     * @throws IllegalStateException в случае если отсутствует запись в хранилище
     * @return DTO описывающую параметры смены
     */
    @Throws(IllegalStateException::class)
    override suspend fun getShiftSettings(): ShiftParamsDTO {
        return shiftParamsDao.getById(1)?.let(mapper::toDTO) ?: throw NoRecordsException
    }

    /**
     * Вставляет или заменяет первую запись в таблице, маппер
     * @param shiftParams DTO описывающая параметры смены, должна иметь идентификатор равный 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun setShiftParams(shiftParams: ShiftParamsDTO) {
        persistenceStoreStrategy.store(shiftParamsDao, mapper, shiftParams)
    }
}