package ru.petrolplus.pos.room.projections

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB

/**
 * Содержит данные текущей смене, общих настройках организации и номере оператора
 * @param shiftParamsDB параметры смены
 * @param operatorNumber номер оператора в смене
 * @param terminalId номер терминала
 * @param relation_id необходим для связки с общими настройками организации
 * @param commonSettingsDB общие настройки организации
 */
class ShiftReceiptHeaderProjection(
    @Embedded
    val shiftParamsDB: ShiftParamsDB,

    @ColumnInfo("operator_number")
    val operatorNumber: Int,

    @ColumnInfo("terminal_id")
    val terminalId: Int,

    private val relation_id: Int,

    @Relation(parentColumn = "relation_id", entityColumn = "id")
    val commonSettingsDB: CommonSettingsDB

)