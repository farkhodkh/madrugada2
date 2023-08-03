package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.BaseSettingsDB

@Dao
interface BaseSettingsDao : BaseDao<BaseSettingsDB> {

    @Query("SELECT * FROM base_settings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): BaseSettingsDB?

}