package ru.petrolplus.pos.p7LibApi.responces

/**
 * Класс содержит код результата работы и возвращённые картой данные при операции по сбросе карты
 * @property resultCode - Код результата выполнения операции
 * @property data - данные ответа
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class OperationResult(
    var resultCode: ResultCode = OK,
    var data: ByteArray = byteArrayOf()
)