package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Класс информации о карте
 * TODO -  Юрий добавь описание
 * @property isReactCard -
 * @property PTC -
 * @property cardId -
 * @property issuerId -
 * @property cardType -
 */
class CardInfo(
    var isReactCard: Boolean,
    var PTC: Int,
    var cardId: Int,
    var issuerId: Int,
    var cardType: CardType
)