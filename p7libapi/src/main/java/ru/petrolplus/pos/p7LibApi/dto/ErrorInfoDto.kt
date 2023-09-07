package ru.petrolplus.pos.p7LibApi.dto

import ru.petrolplus.pos.p7LibApi.responces.OK
import ru.petrolplus.pos.p7LibApi.responces.ResultCode

/**
 * Класс с содержанием ошибки выполнения метода
 * @property errorCode - Код ошибки
 * @property errorStack - Массив результатов
 * @property errorMessage - Текстовое описание ошибки
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class ErrorInfoDto(
    var errorCode: ResultCode = OK,
    var errorStack: Array<Byte> = arrayOf(),
    var errorMessage: String = ""
)