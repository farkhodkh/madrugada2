package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petrolplus.pos.printerapi.Formatting.SIZE_JUSTIFY_WITH_CENTER_MIDDLE
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.ext.formattingReceiptNumber
import ru.petrolplus.pos.printerapi.ext.toCardType
import ru.petrolplus.pos.util.ext.justify
import ru.petrolplus.pos.util.ext.justifyWithCenterMiddle

object GeneralComponents {

    private fun divider(symbol: String) = PrintableDocumentItem(symbol, Format.DIVIDER)
    internal val dashDivider = divider("-")

    internal fun text(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)
    internal fun centredText(text: String) = PrintableDocumentItem(text, Format.CENTER)
    internal fun textJustify(data: Array<String>, paperWidth: Int, offset: Int = 0): IPrintable {
        val content = when (data.size) {
            SIZE_JUSTIFY_WITH_CENTER_MIDDLE -> data.justifyWithCenterMiddle(paperWidth, offset)
            else -> data.justify(paperWidth)
        }
        return PrintableText(content)
    }

    internal fun getShiftReportFootnote() = text(IntroductoryConstruction.FOOTNOTE_CURRENT_PRICE)

    internal fun getReceiptData(title: String, receiptNumber: Long, paperWidth: Int) = arrayOf(
        textJustify(
            arrayOf(title, receiptNumber.formattingReceiptNumber()),
            paperWidth
        ),
    )

    internal fun getOrganizationData(orgName: String, posName: String, inn: String): Array<IPrintable> = arrayOf(
        centredText(orgName),
        centredText("${IntroductoryConstruction.INN} $inn"),
        centredText(posName),
    )

    internal fun getOperatorData(operatorNumber: String, paperWidth: Int) =
        textJustify(arrayOf(IntroductoryConstruction.OPERATOR_NUMBER, operatorNumber), paperWidth)

    internal fun getCardData(cardType: Int, cardNumber: String): Array<IPrintable> = arrayOf(
        text("${IntroductoryConstruction.CARD} ${cardType.toCardType().description}: "),
        text(cardNumber),
    )
}