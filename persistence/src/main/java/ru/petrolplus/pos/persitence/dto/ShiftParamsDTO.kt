package ru.petrolplus.pos.persitence.dto

import java.util.Calendar

/**
 * Содержит данные о текущем номере смены
 * @param id идентификатор
 * @param currentShiftNumber текущий номер смены
 */

data class ShiftParamsDTO(
    override val id: Int = 1,
    val currentShiftNumber: Int,
    val currentShiftStartsTimestamp: Calendar
): IdentifiableDTO