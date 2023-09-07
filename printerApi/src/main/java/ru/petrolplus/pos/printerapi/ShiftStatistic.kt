package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import java.util.Date

data class ShiftStatistic(
    val shiftStarted: Date,
    val commonSettings: CommonSettingsDTO,
    val debit: List<StatisticByService>,
    val returnToCard: List<StatisticByService>,
    val returnToAccount: List<StatisticByService>,
    val countOfDebit: Int,
    val countOfReturn: Int,
    val terminalId: Int,
    val operatorNumber: Long
) {
    private val debitSum =
        debit.sumOf { it.sumByNoRecalculatedTransaction + it.sumByNoRecalculatedTransaction }
    private val returnByCardSum =
        returnToCard.sumOf { it.sumByNoRecalculatedTransaction + it.sumByNoRecalculatedTransaction }
    private val returnByAccountSum =
        returnToAccount.sumOf { it.sumByNoRecalculatedTransaction + it.sumByNoRecalculatedTransaction }

    val sumByAllOperations = debitSum - returnByCardSum - returnByAccountSum
}