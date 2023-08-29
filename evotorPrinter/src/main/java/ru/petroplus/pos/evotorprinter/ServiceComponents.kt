package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petroplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petroplus.pos.printerapi.IntroductoryConstruction.PRICE_UNIT
import ru.petroplus.pos.printerapi.IntroductoryConstruction.SERVICE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.SERVICE_AMOUNT
import ru.petroplus.pos.printerapi.IntroductoryConstruction.SERVICE_PRICE
import ru.petroplus.pos.printerapi.IntroductoryConstruction.SERVICE_SUM
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCurrencyString

object ServiceComponents {
    internal fun serviceTable(
        serviceName: String,
        serviceUnit: String,
        servicePrice: Long,
        sum: Long,
        amount: Long,
        paperWidth: Int
    ): Array<IPrintable> {
        val sumStr = sum.toCurrencyString()
        val amountStr = amount.toAmountString()
        val priceStr = servicePrice.toCurrencyString()

        return arrayOf(
            textJustify(arrayOf(SERVICE, serviceName), paperWidth),
            textJustify(arrayOf(SERVICE_AMOUNT, serviceUnit, amountStr), paperWidth),
            textJustify(arrayOf(SERVICE_PRICE, PRICE_UNIT, priceStr), paperWidth),
            textJustify(arrayOf(SERVICE_SUM, PRICE_UNIT, sumStr), paperWidth),
        )
    }
}