package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.CommonSettingsDB

/**
 * Data Access Object для получения и сохранения общих настроек терминала
 * название таблицы и полей можно посмотреть в [CommonSettingsDB]
 */
@Dao
interface CommonSettingsDao : BaseDao<CommonSettingsDB> {

    /**
     * Метод для получения записи по ее идентификатору
     * @param id идентификатор записи
     * @return сущность олицетворяющую общую настройку в таблицe, может быть null если записи не существует
     */
    @Query("SELECT * FROM common_settings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): CommonSettingsDB?
}
