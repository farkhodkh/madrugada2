package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.GUIDparamsDB

/**
 * Data Access Object для получения и сохранения GUID параметров терминала
 * название таблицы и полей можно посмотреть в [GUIDparamsDB]
 */
@Dao
interface GUIDparamsDao : BaseDao<GUIDparamsDB> {

    /**
     * Метод для получения записи по ее идентификатору
     * @param id идентификатор записи
     * @return сущность олицетворяющую GUID параметры в таблицe, может быть null если записи не существует
     */
    @Query("SELECT * FROM guid_params WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): GUIDparamsDB?

}