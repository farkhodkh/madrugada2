package ru.petroplus.pos.evotorprinter

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import ru.evotor.devices.commons.kkm.KKM
import ru.petroplus.pos.printerapi.DocumentData
import ru.petroplus.pos.printerapi.PrinterRepository
import kotlinx.coroutines.flow.Flow
import ru.evotor.devices.commons.kkm.KkmInfoRequest
import ru.petroplus.pos.evotorprinter.ext.toPrinterDoc
import ru.petroplus.pos.printerapi.BuildConfig
import kotlin.random.Random

class EvotorPrinterRepositoryImpl(private val applicationContext: Context) : PrinterRepository {
    override suspend fun print(data: DocumentData): Flow<Boolean> {
        return flow {
            try {
                val kkm = KKM()
                kkm.connect(applicationContext)

                val paperWidth = kkm.getKkmInfo(KkmInfoRequest()).printerWidthInChar
                val printerDocument = data.toPrinterDoc(paperWidth)

                // Симуляция ошибки во время печати
                if (BuildConfig.DEBUG && false) {
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