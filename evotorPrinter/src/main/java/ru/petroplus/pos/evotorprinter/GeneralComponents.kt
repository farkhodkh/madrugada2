package ru.petroplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.ext.formattingReceiptNumber
import ru.petroplus.pos.printerapi.ext.toCardType
import ru.petroplus.pos.util.ext.justify

object GeneralComponents {

    internal val divider = PrintableDocumentItem("-", Format.DIVIDER)
    internal fun text(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)
    internal fun centredText(text: String) = PrintableDocumentItem(text, Format.CENTER)
    internal fun textJustify(data: Array<String>, paperWidth: Int) =
        PrintableText(data.justify(paperWidth))

    internal fun shiftReportFootnote() = text("* - ${IntroductoryConstruction.FOOTNOTE_CURRENT_PRICE}")


    internal fun receiptData(title: String, receiptNumber: Long, paperWidth: Int) = arrayOf(
        textJustify(
            arrayOf(title, receiptNumber.formattingReceiptNumber()),
            paperWidth
        ),
    )

    internal fun organizationData(orgName: String, posName: String, inn: String): Array<IPrintable> = arrayOf(
        centredText(orgName),
        centredText("${IntroductoryConstruction.INN} $inn"),
        centredText(posName),
    )

    internal fun operatorData(operatorNumber: String, paperWidth: Int) =
        textJustify(arrayOf(IntroductoryConstruction.OPERATOR_NUMBER, operatorNumber), paperWidth)

    internal fun cardData(cardType: Int, cardNumber: String): Array<IPrintable> = arrayOf(
        text("${IntroductoryConstruction.CARD} ${cardType.toCardType().name}: "),
        text(cardNumber),
    )
}