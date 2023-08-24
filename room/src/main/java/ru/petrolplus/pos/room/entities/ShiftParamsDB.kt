package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * Данные о текущем номере смены (всегда изменяется одна запись в таблице)
 * @param id идентификатор записи, первичный ключ
 * @param currentShiftNumber текущий номер смены
 * @param currentShiftStartsTimestamp Дата и время начала текущей смены
 */
@Entity(tableName = "shift_params")
data class ShiftParamsDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,

    @ColumnInfo("current_shift_number")
    val currentShiftNumber: Int,

    @ColumnInfo("current_shift_starts_timestamp")
    val currentShiftStartsTimestamp: Calendar
)