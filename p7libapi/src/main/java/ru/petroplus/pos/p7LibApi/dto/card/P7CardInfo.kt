package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Тип карты P7
 * @property isRecalcCard - TRUE - карта не поддерживает дебет "услуга за услугу", только "услуга за рубли"
 * @property PTC - Количество оставшихся попыток ввода PIN
 * @property cardNumber - Номер карты
 * @property issuerID - Эмитент карты
 * @property cardType - Тип карты
 */
//No need
//class P7CardInfo(
//    override var isRecalcCard: Boolean = false,
//    override var PTC: Byte = 0,
//    override var cardNumber: UInt = 0u,
//    override var issuerID: UInt = 0u,
//    override var cardType: CardType = UnknownCardType
//) : CardInfo

class P7CardInfo(
    var isRecalcCard: Boolean = false,
    var PTC: Byte = 0,
    var cardNumber: Long = 0L,
    var issuerID: Long = 0L,
    var cardType: CardType = UnknownCardType
)