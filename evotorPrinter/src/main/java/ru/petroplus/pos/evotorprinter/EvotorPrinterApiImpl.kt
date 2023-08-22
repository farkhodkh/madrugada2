package ru.petroplus.pos.evotorprinter

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.evotor.devices.commons.kkm.KKM
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.DocumentInflater
import ru.petroplus.pos.printerapi.PrintableDocument
import ru.petroplus.pos.printerapi.PrinterApi
import kotlinx.coroutines.flow.Flow
import ru.evotor.devices.commons.kkm.KkmInfoRequest
import ru.petroplus.pos.printerapi.BuildConfig
import kotlin.random.Random

class EvotorPrinterApiImpl(
    private val applicationContext: Context,
    private val inflater: DocumentInflater<PrinterDocument>
) : PrinterApi {
    override suspend fun print(document: DocumentData): Flow<Boolean> {
        return flow {
            try {
                val kkm = KKM()
                kkm.connect(applicationContext)

                val printerWidth = kkm.getKkmInfo(KkmInfoRequest()).printerWidthInChar

                val doc = PrintableDocument.Debit(document)
                val printerDocument = inflater.inflatePrinterDocument(doc, printerWidth)

                // Симуляция ошибки во время печати
                if (BuildConfig.DEBUG) {
                    delay(300)
                    val isSuccessTry = Random.nextBoolean()
                    if (!isSuccessTry) {
                        emit(false)
                        return@flow
                    }
                }

                kkm.printDocument(printerDocument)
                delay(3000) // TODO: найди другой способ отследить момент окончания печати
                emit(true)
            } catch (e: Exception) {
                emit(false)
            }
        }
    }
}