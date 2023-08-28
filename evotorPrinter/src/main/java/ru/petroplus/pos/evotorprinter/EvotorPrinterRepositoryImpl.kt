package ru.petroplus.pos.evotorprinter

import android.content.Context
import kotlinx.coroutines.delay
import ru.evotor.devices.commons.kkm.KKM
import ru.evotor.devices.commons.kkm.KkmInfoRequest
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petroplus.pos.evatorprinter.R
import ru.petroplus.pos.evotorprinter.ShiftReportComponents.generateShiftReport
import ru.petroplus.pos.evotorprinter.ext.toPrinterDoc
import ru.petroplus.pos.printerapi.PrinterRepository
import ru.petroplus.pos.util.ResourceHelper
import java.util.Date

class EvotorPrinterRepositoryImpl(private val applicationContext: Context) : PrinterRepository {
    private val kkm by lazy {
        val kkm = KKM()
        kkm.connect(applicationContext)
        return@lazy kkm
    }

    private val paperWidth by lazy { kkm.getKkmInfo(KkmInfoRequest()).printerWidthInChar }

    private suspend fun print(printerDocument: PrinterDocument): Exception? = try {
        kkm.printDocument(printerDocument)
        delay(3000) // TODO: найди другой способ отследить момент окончания печати
        null
    } catch (e: Exception) {
        RuntimeException(ResourceHelper.getStringResource(R.string.prining_exception))
    }

    override suspend fun printReceipt(data: ReceiptDTO): Exception? {
        val document = data.toPrinterDoc(paperWidth)
        return print(document)
    }

    override suspend fun printShiftReport(endDate: Date): Exception? {
        val document = generateShiftReport(endDate, paperWidth)
        return print(document)
    }
}
