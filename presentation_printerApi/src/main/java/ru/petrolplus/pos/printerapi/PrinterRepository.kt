package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.persitence.dto.ReceiptDTO

/**
 * Репозиторий для работы с принтером
 */
interface PrinterRepository {
    /**
     * Метод для отправки команды на печать
     * @param data данные о документе для печати
     * @return возвращает Exception в случае ошибки во время процесса печати, null - при отсутствии ошибки
     */
    suspend fun print(data: ReceiptDTO): Exception?
}