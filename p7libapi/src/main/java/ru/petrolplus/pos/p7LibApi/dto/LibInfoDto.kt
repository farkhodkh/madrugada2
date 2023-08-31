package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс который содержит информацию о номере эсквайра и номер терминала, а также номер версии библиотеки.
 * TODO -  Юрий добавь описание
 * @property acquirerId -
 * @property terminalNum -
 * @property majorVersion -
 * @property minerVersion -
 */
class LibInfoDto(
    var acquirerId: Int = 0,
    var terminalNum: Int = 0,
    var majorVersion: Int = 0,
    var minerVersion: Int = 0
)