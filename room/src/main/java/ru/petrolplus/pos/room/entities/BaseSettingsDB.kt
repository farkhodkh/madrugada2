package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Хранит информацию о базовых коммуникационных и идентификационных настройках терминала (всегда хранится только одна запись в таблице)
 * @param id идентификатор записи, первичный ключ
 * @param acquirerId номер эквайера (эмитент терминала)
 * @param terminalId номер терминала
 * @param hostPort порт для подключения к балансировщику АС
 * @param hostIp ip адрес для подключения
 */
@Entity(tableName = "base_settings")
data class BaseSettingsDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int = 1,

    @ColumnInfo("acquirer_id")
    val acquirerId: Int,

    @ColumnInfo("terminal_id")
    val terminalId: Int,

    @ColumnInfo("host_port")
    val hostPort: Int,

    @ColumnInfo("host_ip")
    val hostIp: String
)
