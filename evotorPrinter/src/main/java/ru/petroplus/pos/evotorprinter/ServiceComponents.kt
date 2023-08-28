package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.petroplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.ext.toAmountString
import ru.petroplus.pos.printerapi.ext.toCurrencyString
import ru.petroplus.pos.util.ext.lengthOfSymbols

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

        val spaceOccupied: Int = arrayOf(
            IntroductoryConstruction.SERVICE_SUM, IntroductoryConstruction.PRICE_UNIT, sumStr
        ).lengthOfSymbols()

        // Кол-во "свободного" места при самой длинной строке
        val freeSpace = paperWidth - spaceOccupied
        val rightSpace = freeSpace / 2

        return arrayOf(
            textJustify(arrayOf(IntroductoryConstruction.SERVICE, serviceName), paperWidth),
            serviceLine(
                IntroductoryConstruction.SERVICE_AMOUNT,
                serviceUnit,
                amountStr,
                rightSpace + (sumStr.length - amountStr.length),
                paperWidth
            ),
            serviceLine(
                IntroductoryConstruction.SERVICE_PRICE,
                IntroductoryConstruction.PRICE_UNIT,
                priceStr,
                rightSpace + (sumStr.length - priceStr.length),
                paperWidth
            ),
            serviceLine(
                IntroductoryConstruction.SERVICE_SUM,
                IntroductoryConstruction.PRICE_UNIT,
                sumStr,
                rightSpace,
                paperWidth
            )
        )
    }

    private fun serviceLine(
        title: String, unit: String, value: String, rightSpaceSize: Int, paperWidth: Int
    ): PrintableText {
        val leftSpaceSize = paperWidth - title.length - unit.length - value.length - rightSpaceSize
        val leftSpace =
            " ".repeat(leftSpaceSize)

        val rightSpace = " ".repeat(rightSpaceSize)
        val line = "$title$leftSpace$unit$rightSpace$value"
        return PrintableText(line)
    }
}