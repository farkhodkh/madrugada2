package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petroplus.pos.evotorprinter.ext.toPrinterDoc
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.DocumentInflater

class EvotorDocumentInflater : DocumentInflater<PrinterDocument>() {
    override fun inflateDebit(data: DocumentData): PrinterDocument {
        return data.toPrinterDoc()
    }
}