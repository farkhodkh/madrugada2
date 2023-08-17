package ru.petroplus.pos.evotorprinter

import android.content.Context
import ru.evotor.devices.commons.kkm.KKM
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petroplus.pos.printerapi.DocumentInflater
import ru.petroplus.pos.printerapi.PrinterApi
import ru.petroplus.pos.printerapi.PrintableDocument

class EvotorPrinterApiImpl(
    private val applicationContext: Context,
    private val inflater: DocumentInflater<PrinterDocument>
) : PrinterApi {
    override fun print(document: PrintableDocument) {
        val kkm = KKM()
        kkm.connect(applicationContext)
        val printerDocument = inflater.inflatePrinterDocument(document)
        kkm.printDocument(printerDocument, printCliche = true)
    }
}