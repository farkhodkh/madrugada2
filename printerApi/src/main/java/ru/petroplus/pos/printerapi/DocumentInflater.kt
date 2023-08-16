package ru.petroplus.pos.printerapi

import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petroplus.pos.printerapi.printable.documents.PrintableReceipt

abstract class DocumentInflater <T> {
    fun inflatePrinterDocument(document: PrintableReceipt): T = when (document) {
        is PrintableReceipt.Debit -> inflateDebit(document.data)
    }

    abstract fun inflateDebit(data: TransactionDTO): T
}