package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Данные об общих бизнес-настройках терминала (всегда хранится и изменяется одна запись в таблице)
 * @param id идентификатор записи, первичный ключ
 * @param organizationName название организации
 * @param organizationInn ИНН организации
 * @param posName Название ТО/АЗС
 */
@Entity(tableName = "common_settings")
data class CommonSettingsDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,

    @ColumnInfo("organization_name")
    val organizationName: String,

    @ColumnInfo("organization_inn")
    val organizationInn: String,

    @ColumnInfo("pos_name")
    val posName: String,
)
