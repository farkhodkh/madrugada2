package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс с содержанием ошибки выполнения метода
 * @property errorCode - Код ошибки
 * @property errorStack - Массив результатов
 * @property errorMessage - Текстовое описание ошибки
 */
class ErrorInfoDto(
    var errorCode: Int = 0,
    var errorStack: Array<ResultCode> = arrayOf(),
    var errorMessage: String = ""
)