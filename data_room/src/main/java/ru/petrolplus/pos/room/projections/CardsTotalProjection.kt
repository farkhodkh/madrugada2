package ru.petrolplus.pos.room.projections

import androidx.room.ColumnInfo

/**
 * Содержит информацию о суммарном количестве операций с картами
 * @param totalDebits суммарное количество дебетов
 * @param totalRefunds суммарное количество возвратов
 * @param totalOperations суммарное количество операций
 * @param totalSum итоговая сумма по всем операциям (сумма всех дебетов - сумма всех возвратов)
 */
data class CardsTotalProjection(
    @ColumnInfo("total_debits")
    val totalDebits: Int,

    @ColumnInfo("total_refunds")
    val totalRefunds: Int,

    @ColumnInfo("total_operations")
    val totalOperations: Int,

    @ColumnInfo("total_sum")
    val totalSum: Long,
)
