package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.dashDivider
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.persitence.dto.ServiceTotalDTO
import ru.petrolplus.pos.printerapi.Formatting
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.CURRENT_PRICE_UNIT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.RECALCULATE_MARK
import ru.petrolplus.pos.printerapi.ext.toAmountString
import ru.petrolplus.pos.printerapi.ext.toCurrencyString

fun ServiceTotalDTO.toUi(paperWidth: Int, divider: IPrintable = dashDivider): Array<IPrintable> {
    val price = servicePrice.toCurrencyString()
    val noRecalculatedAmount = totalAmount.toAmountString()
    val noRecalculatedSum = totalSum.toCurrencyString()
    val recalculatedAmount = totalRecalculationAmount.toAmountString()
    val recalculatedSum = totalRecalculationSum.toCurrencyString()

    val serviceUnitOffset = serviceUnit.length - Formatting.BASE_UNIT_LENGTH
    val priceUnitOffset = CURRENT_PRICE_UNIT.length - Formatting.BASE_UNIT_LENGTH - 1

    return arrayOf(
        arrayOf(serviceName, serviceUnit, noRecalculatedAmount).textJustify(paperWidth, serviceUnitOffset),
        arrayOf(price, CURRENT_PRICE_UNIT, noRecalculatedSum).textJustify(paperWidth, priceUnitOffset),
        arrayOf(RECALCULATE_MARK, serviceUnit, recalculatedAmount).textJustify(paperWidth, serviceUnitOffset),
        arrayOf(price, CURRENT_PRICE_UNIT, recalculatedSum).textJustify(paperWidth, priceUnitOffset),
        divider,
    )
}
