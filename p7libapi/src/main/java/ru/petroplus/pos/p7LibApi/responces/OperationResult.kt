package ru.petroplus.pos.p7LibApi.responces

import ru.petroplus.pos.p7LibApi.dto.ResultCode

/**
 * Класс содержит код результата работы и возвращённые картой данные при операции по сбросе карты
 * @property resultCode - Код результат выполнения операции
 * @property data - данные с карты
 */
class OperationResult(
    var resultCode: ResultCode,
    var data: ByteArray
)