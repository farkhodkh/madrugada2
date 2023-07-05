package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Тип карты P7
 * TODO -  Юрий добавь описание
 * @property isReactCard -
 * @property PTC -
 * @property cardId -
 * @property issuerId -
 * @property cardType -
 */
class P7CardInfo(
    override var isReactCard: Boolean,
    override var PTC: Int,
    override var cardId: Int,
    override var issuerId: Int,
    override var cardType: CardType
) : CardInfo