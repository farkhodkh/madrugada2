package ru.petroplus.pos.printerapi

/**
 * Репозиторий для работы с принтером
 */
interface PrinterApi {
    /**
     * Метод для отправки команды на печать
     * @param document тип документа для печати
     */
    fun print(document: PrintableDocument)
}