package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Базовый Data access object реализующий стандартые операции общие для всех таблиц (Вставка, удаление, обновление)
 */
@Dao
interface BaseDao<T> {

    /**
     * Метод для вставки сущности в таблицу. При возникновении конфликтов (данная запись есть в таблице)
     * происходит замена
     * @param entity сохраняемая сущность DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    /**
     * Метод для вставки списка сущностей в таблицу. При возникновении конфликтов (данные записи есть в таблице)
     * происходит замена
     * @param entities сохраняемая коллекция сущностей DB
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: List<T>)

    /**
     * Метод для обновления записи в таблице
     * @param entity обновляемая сущность DB
     */
    @Update
    suspend fun update(entity: T)

    /**
     * Метод для обновления записей в таблице
     * @param entities коллекция с обновляемыми сущностями DB
     */
    @Update
    suspend fun update(entities: List<T>)

    /**
     * Метод для удаления записи в таблице
     * @param entity удаляемая сущность DB
     */
    @Delete
    suspend fun delete(entity: T)

    /**
     * Метод для удаления записей в таблице
     * @param entities коллекция удаляемых сущностей DB
     */
    @Delete
    suspend fun delete(entities: List<T>)
}
