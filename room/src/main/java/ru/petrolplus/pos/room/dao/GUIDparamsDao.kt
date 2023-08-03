package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.GUIDparamsDB

@Dao
interface GUIDparamsDao : BaseDao<GUIDparamsDB> {

    @Query("SELECT * FROM guid_params WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): GUIDparamsDB?

}