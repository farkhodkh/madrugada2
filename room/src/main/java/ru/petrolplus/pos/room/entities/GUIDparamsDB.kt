package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Хранит информацию о последнем номере онлайн-запроса и о параметрах, необходимых для генерации
 * GUID транзакции (хранится только одна запись в таблице)
 *
 * @param id идентификатор записи, первичный ключ
 * @param lastOnlineTransaction число, используемое для формирования номера запроса для отправки на АС
 * @param lastGeneratedTime таймштамп генерации последнего GUID транзакции
 * @param clockSequence номер последовательности для генерации GUID
 * @param hasNodeId признак наличия NodeId
 * @param nodeId параметр для генерации GUID
 */
@Entity(tableName = "guid_params")
data class GUIDparamsDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,

    @ColumnInfo("last_online_transaction")
    val lastOnlineTransaction: Long,

    @ColumnInfo("last_generated_time")
    val lastGeneratedTime: Long,

    @ColumnInfo("clock_sequence")
    val clockSequence: Short,

    @ColumnInfo("has_node_id")
    val hasNodeId: Boolean,

    @ColumnInfo("node_id")
    val nodeId: String,
)
