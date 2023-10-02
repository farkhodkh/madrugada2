package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import ru.petrolplus.pos.persitence.exceptions.NoRecordsException
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для доступа к общим настройкам устройства
 */
interface CommonSettingsPersistence {

    /**
     * Получение общих настроек терминала
     * @return общие настройки терминала
     */
    suspend fun getCommonSettings(): CommonSettingsDTO

    /**
     * Сохранение общих настроек терминала
     * @param commonSettings общие настройки терминала
     */
    suspend fun setCommonSetting(commonSettings: CommonSettingsDTO)
}

class CommonSettingsPersistenceImpl(
    private val commonSettingsDao: CommonSettingsDao,
    private val mapper: Mapper<CommonSettingsDTO, CommonSettingsDB>,
    private val persistenceStoreStrategy: StoreStrategy,
) : CommonSettingsPersistence {

    /**
     * Получает первую запись из хранилища
     * @throws IllegalStateException в случае если отсутствует запись в хранилище
     * @return DTO описывающую общие настройки
     */
    @Throws(IllegalStateException::class)
    override suspend fun getCommonSettings(): CommonSettingsDTO {
        return commonSettingsDao.getById(1)?.let(mapper::toDTO) ?: throw NoRecordsException
    }

    /**
     * Вставляет или заменяет первую запись в таблице
     * @param commonSettings DTO описывающая общие настройки приложения, идентификатор должен равняться 1
     * @throws IllegalArgumentException в случае если id записи не равно 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun setCommonSetting(commonSettings: CommonSettingsDTO) {
        persistenceStoreStrategy.store(commonSettingsDao, mapper, commonSettings)
    }
}
