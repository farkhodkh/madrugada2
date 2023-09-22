package ru.petrolplus.pos.room.projections

import androidx.room.ColumnInfo
import androidx.room.Embedded
import ru.petrolplus.pos.room.entities.ServiceDB

/**
 * Проекция содержащая услугу и суммарную информацию по ней за определенный период
 * @param serviceDb сервис или услуга
 * @param totalAmount суммарное количество предоставленных услуг за период
 * @param totalSum сумма всех предоставленных услуг за период
 * @param totalRecalculationAmount суммарное количество предоставленных услуг за период по пересчетным операциям
 * @param totalRecalculationSum сумма всех углуг по пересчетным операциям
 */
data class TransactionByServiceProjection(
    @Embedded
    val serviceDb: ServiceDB,

    @ColumnInfo("total_amount")
    val totalAmount: Long,

    @ColumnInfo("total_sum")
    val totalSum: Long,

    @ColumnInfo("total_recalc_amount")
    val totalRecalculationAmount: Long,

    @ColumnInfo("total_recalc_sum")
    val totalRecalculationSum: Long
)