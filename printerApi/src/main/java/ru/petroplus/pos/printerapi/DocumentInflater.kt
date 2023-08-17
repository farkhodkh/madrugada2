package ru.petroplus.pos.printerapi

abstract class DocumentInflater <T> {
    fun inflatePrinterDocument(document: PrintableDocument): T = when (document) {
        is PrintableDocument.Debit -> inflateDebit(document.data)
    }

    abstract fun inflateDebit(data: DocumentData): T
}