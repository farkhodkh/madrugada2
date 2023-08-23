package ru.petroplus.pos.printerapi

import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для работы с принтером
 */
interface PrinterRepository {
    /**
     * Метод для отправки команды на печать
     * @param data данные о документе для печати
     */
    suspend fun print(data: DocumentData): Flow<Boolean>

}