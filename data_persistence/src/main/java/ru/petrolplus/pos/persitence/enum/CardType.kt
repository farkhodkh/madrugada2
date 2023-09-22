package ru.petrolplus.pos.persitence.enum

import ru.petrolplus.pos.R
import ru.petrolplus.pos.resources.ResourceHelper

/**
 * Энумерация представляющая тип карты
 * @param id целочисленный идентификатор карты
 * @param description текстовое описание типа карты
 */
enum class CardType(val id: Int, val description: String) {
    UNKNOWN(0, ResourceHelper.getStringResource(R.string.unknown_card_type)),
    PETROL_5(1, ResourceHelper.getStringResource(R.string.petrol5)),
    PETROL_7(2, ResourceHelper.getStringResource(R.string.petrol7));
}