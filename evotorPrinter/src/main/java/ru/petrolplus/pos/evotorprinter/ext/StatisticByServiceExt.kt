package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.divider
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.printerapi.Formatting
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.StatisticByService
import ru.petrolplus.pos.printerapi.ext.toAmountString
import ru.petrolplus.pos.printerapi.ext.toCurrencyString

fun StatisticByService.toUi(paperWidth: Int): Array<IPrintable> {
    val price = service.price.toCurrencyString()
    val priceUnit = IntroductoryConstruction.CURRENT_PRICE_UNIT
    val recalculateMark = IntroductoryConstruction.RECALCULATE_MARK
    val noRecalculatedAmount = amountByNoRecalculatedTransaction.toAmountString()
    val noRecalculatedSum = sumByNoRecalculatedTransaction.toCurrencyString()
    val recalculatedAmount = amountByRecalculatedTransaction.toAmountString()
    val recalculatedSum = sumByRecalculatedTransaction.toCurrencyString()

    val serviceUnitOffset = service.unit.length - Formatting.BASE_UNIT_LENGTH
    val priceUnitOffset = priceUnit.length - Formatting.BASE_UNIT_LENGTH - 1

    return arrayOf(
        textJustify(
            arrayOf(service.name, service.unit, noRecalculatedAmount),
            paperWidth,
            serviceUnitOffset
        ),
        textJustify(arrayOf(price, priceUnit, noRecalculatedSum), paperWidth, priceUnitOffset),
        textJustify(
            arrayOf(recalculateMark, service.unit, recalculatedAmount),
            paperWidth,
            serviceUnitOffset
        ),
        textJustify(arrayOf(price, priceUnit, recalculatedSum), paperWidth, priceUnitOffset),
        divider
    )
}