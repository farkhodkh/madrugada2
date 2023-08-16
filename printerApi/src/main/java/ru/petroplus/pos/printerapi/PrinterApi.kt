package ru.petroplus.pos.printerapi

import ru.petroplus.pos.printerapi.printable.documents.PrintableReceipt

/**
 * Репозиторий для работы с принтером
 */
interface PrinterApi {
    /**
     * Метод для отправки команды на печать
     * @param documentType тип документа для печати
     */
    fun print(document: PrintableReceipt)
}