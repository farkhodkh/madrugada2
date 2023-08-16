package ru.petroplus.pos.printerapi.printable.documents

sealed class PrintableDocument() {
    class Debit(val data: DebitReceipt): PrintableDocument()

    // TODO: добавить: возврат, отказ, сменный отчет
}