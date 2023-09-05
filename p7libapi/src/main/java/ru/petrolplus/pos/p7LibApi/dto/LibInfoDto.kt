package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс который содержит информацию о номере эсквайра и номер терминала, а также номер версии библиотеки.
 * @property acquirerId - ID экваэра
 * @property terminalNum - Номер терминала
 * @property majorVersion - Старший номер версии библиотеки
 * @property minerVersion - Младший номер версии бибилиотеки
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class LibInfoDto(
    var acquirerId: Long = 0,
    var terminalNum: Long = 0,
    var majorVersion: Long = 0,
    var minerVersion: Long = 0
)