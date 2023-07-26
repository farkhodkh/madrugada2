package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс который содержит информацию о номере эсквайра и номер терминала, а также номер версии библиотеки.
 * TODO -  Юрий добавь описание
 * @property acquirerId - ID экваэра
 * @property terminalNum - Номер терминала
 * @property majorVersion - Старший номер версии библиотеки
 * @property minerVersion - Младший номер версии бибилиотеки
 */
class LibInfoDto(
    var acquirerId:   Long = 0,
    var terminalNum:  Long = 0,
    var majorVersion: Long = 0,
    var minerVersion: Long = 0
)