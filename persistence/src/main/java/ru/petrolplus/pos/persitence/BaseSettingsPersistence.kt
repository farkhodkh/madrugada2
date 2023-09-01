package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import java.lang.IllegalArgumentException

/**
 * Интерфейс для доступа к базовым настройкам устройства
 */
interface BaseSettingsPersistence {
    /**
     * Получение базовых настроек терминала
     * @return базовые настройки терминала
     */
    suspend fun getBaseSettings(): BaseSettingsDTO

    /**
     * Сохранение базовых настроек терминала
     * @param baseSetting базовые настройки терминала
     */
    suspend fun setBaseSettings(baseSetting: BaseSettingsDTO)
}

class BaseSettingsPersistenceImpl(
    private val baseSettingsDao: BaseSettingsDao,
    private val mapper: Mapper<BaseSettingsDTO, BaseSettingsDB>,
    private val persistenceStoreStrategy: StoreStrategy
) : BaseSettingsPersistence {

    /**
     * Получает первую запись из хранилища
     * @throws IllegalStateException в случае если отсутствует запись в хранилище
     * @return DTO описывающую базовые настройки
     */
    @Throws(IllegalStateException::class)
    override suspend fun getBaseSettings(): BaseSettingsDTO {
        return baseSettingsDao.getById(1)?.let(mapper::toDTO) ?: throw NoRecordsException
    }

    /**
     * Вставляет или заменяет первую запись в таблице
     * @param baseSetting DTO описывающая базовые настройки приложения, идентификатор должен равняться 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @kotlin.jvm.Throws(IllegalArgumentException::class)
    override suspend fun setBaseSettings(baseSetting: BaseSettingsDTO) {
        persistenceStoreStrategy.store(baseSettingsDao, mapper, baseSetting)
    }

}