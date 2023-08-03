package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.ShiftParamsDB

@Dao
interface ShiftParamsDao : BaseDao<ShiftParamsDB> {

    @Query("SELECT * FROM shift_params WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ShiftParamsDB?

}