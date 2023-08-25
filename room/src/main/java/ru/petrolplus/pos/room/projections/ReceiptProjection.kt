package ru.petrolplus.pos.room.projections

import androidx.room.Embedded
import androidx.room.Relation
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.TransactionDB

/**
 * Проекция для общего запроса для чеков дебета
 * @param transactionDB представляет тут данные по транзакциям
 * @param relation_id неободима чтобы связать CommonSettingsDB и ReceiptParamsDB с транзакцией
 * @param receiptParamsDB представляет тут данные по номеру чека
 * @param commonSettingsDB представляет тут основные настройки
 * @param serviceDB представляет тут связанную услугу с найденной транзакции по [TransactionDB.serviceIdWhat]
 */
data class ReceiptProjection(
    @Embedded
    val transactionDB: TransactionDB,

    //Запросом создаем фейковый внешний ключ для receipt_params и common_settings
    private val relation_id: Long,

    @Relation(parentColumn = "relation_id", entityColumn = "id")
    val receiptParamsDB: ReceiptParamsDB,

    @Relation(parentColumn = "relation_id", entityColumn = "id")
    val commonSettingsDB: CommonSettingsDB,

    @Relation(parentColumn = "service_id_what", entityColumn = "id")
    val serviceDB: ServiceDB
)

