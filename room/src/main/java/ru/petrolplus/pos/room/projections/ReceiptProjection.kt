package ru.petrolplus.pos.room.projections

import androidx.room.Embedded
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.TransactionDB

/**
 * Проекция для общего запроса для чеков дебета
 * префиксы нужны из-за коллизий некоторых свойств
 */
data class ReceiptProjection(
    @Embedded
    val transactionDB: TransactionDB,

    @Embedded("r_")
    val receiptParamsDB: ReceiptParamsDB,

    @Embedded("c_")
    val commonSettingsDB: CommonSettingsDB,

    @Embedded("s_")
    val serviceDB: ServiceDB
)

