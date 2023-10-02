package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.BaseSettingsDB

/**
 * Data Access Object для получения и сохранения базовых настроек терминала
 * название таблицы и полей можно посмотреть в [BaseSettingsDB]
 */
@Dao
interface BaseSettingsDao : BaseDao<BaseSettingsDB> {

    /**
     * Метод для получения записи по ее идентификатору
     * @param id идентификатор записи
     * @return сущность олицетворяющую базовую настройку в таблице, может быть null если записи не существует
     */
    @Query("SELECT * FROM base_settings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): BaseSettingsDB?
}
