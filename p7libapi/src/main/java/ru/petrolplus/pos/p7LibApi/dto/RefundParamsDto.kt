package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс содержит параметры для выполнения операции возврата (код купленной услуги,
 * а также цену, количество и стоимость услуги)
 * @property serviceWhat - Номер возвращаемой услуги (service_what == 1)
 * @property amount - Количество услуги (amount = 100 (1 л))
 * @property price - Цена услуги (price == 1000 (1р))
 * @property sum - Сумма (sum == 1000(1р))
 */
class RefundParamsDto(
    var serviceWhat: Int = 0,
    var amount: Long = 0,
    var price: Long = 0,
    var sum: Long = 0,
)