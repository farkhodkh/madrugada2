package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.persitence.dto.ShiftReceiptDTO
import java.util.Date

/**
 * Репозиторий для работы с принтером
 */
interface PrinterRepository {
    /**
     * Метод для отправки команды на печать чка
     * @param data данные о документе для печати
     * @return возвращает Exception в случае ошибки во время процесса печати, null - при отсутствии ошибки
     */
    suspend fun printReceipt(data: ReceiptDTO): Exception?

    /**
     * Метод для отправки команды на печать сменного отчета
     * @param receipt данные по сервисами использованым за смену по типам операций
     * @param endDate дата-время окончания смены
     * @return возвращает Exception в случае ошибки во время процесса печати, null - при отсутствии ошибки
     */
    suspend fun printShiftReport(receipt: ShiftReceiptDTO, endDate: Date): Exception?
}