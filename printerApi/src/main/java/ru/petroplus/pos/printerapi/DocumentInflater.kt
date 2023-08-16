package ru.petroplus.pos.printerapi

import ru.petroplus.pos.printerapi.printable.documents.DebitReceipt
import ru.petroplus.pos.printerapi.printable.documents.PrintableDocument

abstract class DocumentInflater <T> {
    fun inflatePrinterDocument(document: PrintableDocument): T = when (document) {
        is PrintableDocument.Debit -> inflateDebit(document.data)
    }

    abstract fun inflateDebit(data: DebitReceipt): T
}