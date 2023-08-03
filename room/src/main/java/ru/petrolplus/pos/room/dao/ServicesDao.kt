package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.ServiceDB

@Dao
interface ServicesDao : BaseDao<ServiceDB> {

    @Query("SELECT * FROM services WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ServiceDB?

    @Query("SELECT * FROM services")
    suspend fun getAll(): List<ServiceDB>

}