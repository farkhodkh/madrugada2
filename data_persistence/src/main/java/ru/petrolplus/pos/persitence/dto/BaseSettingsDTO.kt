package ru.petrolplus.pos.persitence.dto

/**
 * DTO
 * Содержит информацию о базовых коммуникационных и идентификационных настройках терминала
 * @param id идентификатор
 * @param acquirerId номер эквайера (эмитент терминала)
 * @param terminalId номер терминала
 * @param hostPort порт для подключения к балансировщику АС
 * @param hostIp ip адрес для подключения
 */
data class BaseSettingsDTO(
    override val id: Int = 1,
    val acquirerId: Int,
    val terminalId: Int,
    val hostPort: Int,
    val hostIp: String,
) : IdentifiableDTO
