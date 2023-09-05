package ru.petrolplus.pos.p7LibApi.dto.card

/**
 * Класс информации о карте
 * @property isRecalcCard - TRUE - карта не поддерживает дебет "услуга за услугу", только "услуга за рубли"
 * @property PTC - Количество оставшихся попыток ввода PIN
 * @property cardNumber - Номер карты
 * @property issuerID - Эмитент карты
 * @property cardType - Тип карты
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class CardInfo(
    var isRecalcCard: Boolean = false,
    var PTC: Byte = 0,
    var cardNumber: Long = 0L,
    var issuerID: Long = 0L,
    var cardType: CardType = UnknownCardType
)