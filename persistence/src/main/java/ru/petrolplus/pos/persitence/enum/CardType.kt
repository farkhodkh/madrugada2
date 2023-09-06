package ru.petrolplus.pos.persitence.enum

/**
 * Энумерация представляющая тип карты
 * @param id целочисленный идентификатор карты
 */
enum class CardType(val id: Int) {
    UNKNOWN(0),
    PETROL_5(1),
    PETROL_7(2);
}