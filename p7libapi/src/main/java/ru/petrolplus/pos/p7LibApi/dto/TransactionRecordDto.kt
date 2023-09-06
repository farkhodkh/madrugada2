package ru.petrolplus.pos.p7LibApi.dto

/**
 * @property cardNumber - Графический номер карты
 * @property shiftNumber - Номер смены
 * @property timeStamp - Время проведения операции
 * @property serviceIdOrigEmit - Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
 * @property serviceIdCurrEmit - Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
 * @property totalVolume - Количество топлива/услуги ("что покупали")
 * @property price - Цена за 1 ед. топлива/услуги ("что покупали")
 * @property totalSum - Сумма (TotalVolume * Price)
 * @property cardTrzCounter - Номер операции (в терминах карты)
 * @property hasReturn - Был ли возврат/отмена (0 - нет, 1 - да)
 * @property rollbackCode - Код для возврата (получен от карты во время дебета)
 * @property debitToken - GUID транзакции дебета в онлайне
 * @property terminalNumber - Номер терминала/POS/поста
 * @property crc32 - CRC32 для данной записи
 * @property operationType - Тип транзакции 0 - неизвестно (только для поиска: искать любой тип операции)
 *                                          1 - дебет
 *                                          2 - кредит кошелька
 *                                          3 - онлайн-пополнение счета
 *                                          4 - возврат на карту (для карт "H")
 *                                          5 - возврат на счет (для карт "J")
 * @property cardType - Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
 * @property clientSum - Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
 * @property deltaBonus - Начисленные бонусы при транзакции с лояльностью
 * @property returnTimeStamp - Время проведения операции возврата/отмены по данному дебету/кредиту
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class TransactionRecordDto(
    var cardNumber: Long = 0L,
    var shiftNumber: Long = 0,
    var timeStamp: ClockDto = ClockDto(),
    var serviceIdOrigEmit: Byte = 0,
    var serviceIdCurrEmit: Byte = 0,
    var totalVolume: Long = 0L,
    var price: Long = 0L,
    var totalSum: Long = 0L,
    var cardTrzCounter: Int = 0,
    var hasReturn: Boolean = false,
    var rollbackCode: ByteArray = byteArrayOf(),
    var debitToken: ByteArray = byteArrayOf(),
    var terminalNumber: Int = 0,
    var crc32: ByteArray = byteArrayOf(),
    var operationType: Byte = 0,
    var cardType: Byte = 0,
    var clientSum: Long = 0L,
    var deltaBonus: Long = 0L,
    var returnTimeStamp: ClockDto = ClockDto()
)