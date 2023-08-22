package ru.petroplus.pos.printerapi

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с принтером
 */
interface PrinterApi {
    /**
     * Метод для отправки команды на печать
     * @param document тип документа для печати
     */
    suspend fun print(document: DocumentData): Flow<Boolean>

}