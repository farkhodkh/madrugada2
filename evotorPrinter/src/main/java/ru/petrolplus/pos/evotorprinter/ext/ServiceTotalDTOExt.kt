package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.dashDivider
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.persitence.dto.ServiceTotalDTO
import ru.petrolplus.pos.printerapi.Formatting
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.ext.toAmountString
import ru.petrolplus.pos.printerapi.ext.toCurrencyString

fun ServiceTotalDTO.toUi(paperWidth: Int, divider: IPrintable = dashDivider): Array<IPrintable> {
    val price = servicePrice.toCurrencyString()
    val priceUnit = IntroductoryConstruction.CURRENT_PRICE_UNIT
    val recalculateMark = IntroductoryConstruction.RECALCULATE_MARK

    val noRecalculatedAmount = totalAmount.toAmountString()
    val noRecalculatedSum = totalSum.toCurrencyString()
    val recalculatedAmount = totalRecalculationAmount.toAmountString()
    val recalculatedSum = totalRecalculationSum.toCurrencyString()

    val serviceUnitOffset = serviceUnit.length - Formatting.BASE_UNIT_LENGTH
    val priceUnitOffset = priceUnit.length - Formatting.BASE_UNIT_LENGTH - 1

    return arrayOf(
        textJustify(
            arrayOf(serviceName, serviceUnit, noRecalculatedAmount),
            paperWidth,
            serviceUnitOffset
        ),
        textJustify(arrayOf(price, priceUnit, noRecalculatedSum), paperWidth, priceUnitOffset),
        textJustify(
            arrayOf(recalculateMark, serviceUnit, recalculatedAmount),
            paperWidth,
            serviceUnitOffset
        ),
        textJustify(arrayOf(price, priceUnit, recalculatedSum), paperWidth, priceUnitOffset),
        divider
    )
}