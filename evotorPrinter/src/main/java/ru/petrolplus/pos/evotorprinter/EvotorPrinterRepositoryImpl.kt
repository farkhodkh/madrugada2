package ru.petrolplus.pos.evotorprinter

import android.content.Context
import kotlinx.coroutines.delay
import ru.evotor.devices.commons.kkm.KKM
import ru.evotor.devices.commons.kkm.KkmInfoRequest
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.evatorprinter.R
import java.util.Date
import ru.petrolplus.pos.evotorprinter.ext.toPrinterDoc
import ru.petrolplus.pos.evotorprinter.ext.toUi
import ru.petrolplus.pos.persitence.dto.ShiftReceiptDTO
import ru.petrolplus.pos.printerapi.PrinterRepository
import ru.petrolplus.pos.resources.ResourceHelper

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

    override suspend fun printShiftReport(receipt: ShiftReceiptDTO, endDate: Date): Exception? {
        val document = receipt.toUi(endDate, paperWidth)
        return print(document)
    }
}
