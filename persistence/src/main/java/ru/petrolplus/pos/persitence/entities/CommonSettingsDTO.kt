package ru.petrolplus.pos.persitence.entities

/**
 * Содержит данные об общих бизнес-настройках терминала
 * @param id идентификатор
 * @param organizationName название организации
 * @param organizationInn ИНН организации
 * @param posName Название ТО/АЗС
 */
data class CommonSettingsDTO(
    override val id: Int = 1,
    val organizationName: String,
    val organizationInn: String,
    val posName: String,
): IdentifiableDTO
