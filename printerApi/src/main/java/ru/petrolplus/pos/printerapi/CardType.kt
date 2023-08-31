package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.util.ResourceHelper

sealed class CardType(val name: String) {
    object Petrol5: CardType(ResourceHelper.getStringResource(R.string.petrol5))
    object Petrol7: CardType(ResourceHelper.getStringResource(R.string.petrol7))
    object Unknown: CardType(ResourceHelper.getStringResource(R.string.unknown_card_type))
}