package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс описывающий переметры терминала при чтения настроек терминала (.ini файл)
 * с последуюей передачей этих параметров из при инициализации библиотеки P7Lib
 *
 * @property acquirerId - Идентификатор продавца
 * @property terminalId - Идентификатор терминала
 * @property hostIp - IP адрес провайдера
 * @property hostPort - Port адреса провайдера
 *
 * @author - @FAHA
 */
class InitDataDto(
    var acquirerId: Int = 0,
    var terminalId: Int = 0,
    var hostIp: String = "",
    var hostPort: Int = 0,
)