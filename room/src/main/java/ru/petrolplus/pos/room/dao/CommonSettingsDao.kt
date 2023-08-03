package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.CommonSettingsDB

@Dao
interface CommonSettingsDao : BaseDao<CommonSettingsDB> {

    @Query("SELECT * FROM common_settings WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): CommonSettingsDB?

}