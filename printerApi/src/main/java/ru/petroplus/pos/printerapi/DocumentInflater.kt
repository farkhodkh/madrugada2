package ru.petroplus.pos.printerapi

abstract class DocumentInflater <T> {
    fun inflatePrinterDocument(document: PrintableDocument, printerWidth: Int): T = when (document) {
        is PrintableDocument.Debit -> inflateDebit(document.data, printerWidth)
    }

    abstract fun inflateDebit(data: DocumentData, printerWidth: Int): T
}