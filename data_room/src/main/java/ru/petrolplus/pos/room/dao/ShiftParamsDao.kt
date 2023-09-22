package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.ShiftParamsDB

/**
 * Data Access Object для получения и сохранения параметров смены
 * название таблицы и полей можно посмотреть в [ShiftParamsDB]
 */
@Dao
interface ShiftParamsDao : BaseDao<ShiftParamsDB> {

    /**
     * Метод для получения записи по ее идентификатору
     * @param id идентификатор записи
     * @return сущность олицетворяющую параметры смены в таблице, может быть null если записи не существует
     */
    @Query("SELECT * FROM shift_params WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ShiftParamsDB?

}