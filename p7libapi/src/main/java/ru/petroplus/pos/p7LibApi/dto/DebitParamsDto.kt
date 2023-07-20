package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс параметры Дебет операции
 * @property serviceWhat - Номер покупаемой услуги (service_what == 1)
 * @property serviceFrom - Номер расчетной услуги, т.е. с которой списываются средства (service_from == 1)
 * @property amount - Количество услуги (amount = 100 (1 л))
 * @property price - Цена услуги (price == 1000 (1р))
 * @property sum - Сумма (sum == 1000(1р))
 * @property pinBlock - Введенный PIN предъявителем карты в виде PIN-блока зашифрованного публичной частью
 * RSA ключа и Nonce, предоставленного управляющему приложению ранее на этапе DetectCard
 */
class DebitParamsDto(
    var serviceWhat: Int = 0,
    var serviceFrom: Int = 0,
    var amount: UInt = 0u,
    var price:  UInt = 0u,
    var sum:    UInt = 0u,
    var pinBlock: ByteArray = byteArrayOf(),
)