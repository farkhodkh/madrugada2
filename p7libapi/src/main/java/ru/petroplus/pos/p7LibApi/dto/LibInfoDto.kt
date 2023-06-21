package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс который содержит информацию о номере эсквайра и номер терминала, а также номер версии библиотеки.
 * TODO - Добавить описание свойств
 * @property acquirerId -
 * @property terminalNum -
 * @property majorVersion -
 * @property minerVersion -
 */
class LibInfoDto(
    var acquirerId: Int,
    var terminalNum: Int,
    var majorVersion: Int,
    var minerVersion: Int
)