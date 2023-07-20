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
    override var isRecalcCard: Boolean = false,
    override var PTC: Byte = 0,
    override var cardNumber: UInt = 0u,
    override var issuerID: UInt = 0u,
    override var cardType: CardType = UnknownCardType
) : CardInfo