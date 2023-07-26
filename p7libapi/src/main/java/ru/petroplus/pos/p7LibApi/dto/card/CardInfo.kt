package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Interface информации о карте
 * TODO -  Юрий добавь описание
 * @property isRecalcCard - TRUE - карта не поддерживает дебет "услуга за услугу", только "услуга за рубли"
 * @property PTC - Количество оставшихся попыток ввода PIN
 * @property cardNumber - Номер карты
 * @property issuerID - Эмитент карты
 * @property cardType - Тип карты
 */
interface CardInfo{
    var isRecalcCard: Boolean
    var PTC: Byte
    var cardNumber: Long
    var issuerID: Long
    var cardType: CardType
}