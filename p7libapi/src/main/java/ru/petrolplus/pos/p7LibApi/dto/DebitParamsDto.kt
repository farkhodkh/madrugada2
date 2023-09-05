package ru.petrolplus.pos.p7LibApi.dto

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

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class DebitParamsDto(
    var serviceWhat: Int = 0,
    var serviceFrom: Int = 0,
    var amount: Long = 0L,
    var price: Long = 0L,
    var sum: Long = 0L,
    var pinBlock: ByteArray = byteArrayOf()
)