package ru.petroplus.pos.evotorprinter

import android.content.Context
import kotlinx.coroutines.delay
import ru.evotor.devices.commons.kkm.KKM
import ru.evotor.devices.commons.kkm.KkmInfoRequest
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petroplus.pos.evatorprinter.R
import ru.petroplus.pos.evotorprinter.ext.toPrinterDoc
import ru.petroplus.pos.printerapi.PrinterRepository
import ru.petroplus.pos.util.ResourceHelper

class EvotorPrinterRepositoryImpl(private val applicationContext: Context) : PrinterRepository {
    override suspend fun print(data: ReceiptDTO): Exception? {
        val kkm = KKM()
        kkm.connect(applicationContext)

        return try {
            val paperWidth = kkm.getKkmInfo(KkmInfoRequest()).printerWidthInChar
            val printerDocument = data.toPrinterDoc(paperWidth)
            kkm.printDocument(printerDocument)
            delay(3000) // TODO: найди другой способ отследить момент окончания печати
            null
        } catch (e: Exception) {
            RuntimeException(ResourceHelper.getStringResource(R.string.prining_exception))
        } finally {
            kkm.disconnect()
        }
    }
}
