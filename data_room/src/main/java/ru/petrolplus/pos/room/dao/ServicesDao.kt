package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.entities.ServiceDB

/**
 * Data Access Object для получения и сохранения данных о сервисах и услугах
 * название таблицы и полей можно посмотреть в [ServiceDB]
 */
@Dao
interface ServicesDao : BaseDao<ServiceDB> {

    /**
     * Метод для получения сервиса или услуги по идентификатору
     * @param id идентификатор сервиса или услуги
     * @return сущность олицетворяющую сервис или услугу в таблицe, может быть null если не удалось
     * найти
     */
    @Query("SELECT * FROM services WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): ServiceDB?

    /**
     * Метод для получения всех сервисов и услуг
     * @return коллекцию с сущностями олицетворяющими сервис или услугу, коллекция может быть пустой если нет записей
     */
    @Query("SELECT * FROM services")
    suspend fun getAll(): List<ServiceDB>
}
