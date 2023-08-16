package ru.petroplus.pos.evatorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petroplus.pos.evatorprinter.ext.toPrinterDoc
import ru.petroplus.pos.printerapi.DocumentInflater

class EvotorDocumentInflater : DocumentInflater<PrinterDocument>() {
    override fun inflateDebit(data: TransactionDTO): PrinterDocument {
        return data.toPrinterDoc()
    }
}