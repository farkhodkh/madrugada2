package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Interface информации о карте
 * TODO -  Юрий добавь описание
 * @property isReactCard -
 * @property PTC -
 * @property cardId -
 * @property issuerId -
 * @property cardType -
 */
interface CardInfo{
    var isReactCard: Boolean
    var PTC: Int
    var cardId: Int
    var issuerId: Int
    var cardType: CardType
}