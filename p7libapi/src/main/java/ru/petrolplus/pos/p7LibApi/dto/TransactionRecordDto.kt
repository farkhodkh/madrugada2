package ru.petrolplus.pos.p7LibApi.dto

package ru.petroplus.pos.p7LibApi.dto

class TransactionRecordDto(
    /**
    *  Графический номер карты
    */
    var cardNumber: Long = 0L,
    /**
     *Номер смены
     */
    var shiftNumber: Long = 0,
    /**
     * Время проведения операции
     */
    var timeStamp: ClockDto = ClockDto(),
    /**
     * Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
     */
    var serviceIdOrigEmit: Byte = 0,
    /**
     * Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
     */
    var serviceIdCurrEmit: Byte = 0,
    /**
     * Количество топлива/услуги ("что покупали")
     */
    var totalVolume: Long = 0L,
    /**
     * Цена за 1 ед. топлива/услуги ("что покупали")
     */
    var price: Long = 0L,
    /**
     * Сумма (TotalVolume * Price)
     */
    var totalSum: Long = 0L,
    /**
     * Номер операции (в терминах карты)
     */
    var cardTrzCounter: Int = 0,
    /**
     * Был ли возврат/отмена (0 - нет, 1 - да)
     */
    var hasReturn: Boolean = false,
    /**
     * Код для возврата (получен от карты во время дебета)
     */
    var rollbackCode: ByteArray = byteArrayOf(),
    /**
     * GUID транзакции дебета в онлайне
     */
    var debitToken: ByteArray = byteArrayOf(),
    /**
     * Номер терминала/POS/поста
     */
    var terminalNumber: Int = 0,
    /**
     * CRC32 для данной записи
     */
    var crc32: ByteArray = byteArrayOf(),          
    /**
     * Тип транзакции 0 - неизвестно (только для поиска: искать любой тип операции)
     *                1 - дебет
     *                2 - кредит кошелька
     *                3 - онлайн-пополнение счета
     *                4 - возврат на карту (для карт "H")
     *                5 - возврат на счет (для карт "J")
     */
    var operationType: Byte = 0,
    /**
     * Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
     */
    var cardType: Byte = 0,
    /**
     * Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
     */
    var clientSum: Long = 0L,
    /**
     * Начисленные бонусы при транзакции с лояльностью
     */
    var deltaBonus: Long = 0L,
    /**
     * Время проведения операции возврата/отмены по данному дебету/кредиту
     */
    var returnTimeStamp: ClockDto = ClockDto()
)