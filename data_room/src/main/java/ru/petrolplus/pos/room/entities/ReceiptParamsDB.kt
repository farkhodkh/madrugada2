package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Данные о номере последнего напечатанного чека
 * @param id идентификатор записи, первичный ключ
 * @param receiptNumber Номер последнего напечатанного чека
 */
@Entity(tableName = "receipt_params")
data class ReceiptParamsDB(

    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,

    @ColumnInfo("current_receipt_number")
    val receiptNumber: Long,
)
