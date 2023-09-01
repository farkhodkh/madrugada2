package ru.petroplus.pos.p7LibApi.responces

import ru.petroplus.pos.p7LibApi.dto.OK
import ru.petroplus.pos.p7LibApi.dto.ResultCode

/**
 * Класс содержит код результата работы и возвращённые картой данные при операции по сбросе карты
 * @property resultCode - Код результата выполнения операции
 * @property data - данные ответа
 */
class OperationResult(
    var resultCode: ResultCode = OK,
    var data: ByteArray = byteArrayOf()
)