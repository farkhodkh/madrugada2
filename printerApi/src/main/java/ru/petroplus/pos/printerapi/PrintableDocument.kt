package ru.petroplus.pos.printerapi


// TODO: избавится от прослойки
sealed class PrintableDocument() {
    class Debit(val data: DocumentData): PrintableDocument()

    // TODO: добавить: возврат, отказ, сменный отчет
}