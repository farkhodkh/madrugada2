package ru.petrolplus.pos.persitence.entities

/**
 * Содержит данные о текущем номере смены
 * @param id идентификатор
 * @param currentShiftNumber текущий номер смены
 */

data class ShiftParamsDTO(
    override val id: Int = 1,
    val currentShiftNumber: Int,
): IdentifiableDTO