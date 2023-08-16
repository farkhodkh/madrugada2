package ru.petroplus.pos.evatorprinter

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.evotor.devices.commons.utils.Format
import ru.evotor.devices.commons.utils.PrintableDocumentItem
import ru.petroplus.pos.printerapi.DocumentInflater
import ru.petroplus.pos.printerapi.printable.documents.DebitReceipt

class EvotorDocumentInflater : DocumentInflater<PrinterDocument>() {

    val divider = PrintableDocumentItem("-", Format.DIVIDER)
    fun centred(text: String) = PrintableDocumentItem(text, Format.CENTER)
    fun simple(text: String) = PrintableDocumentItem(text, Format.LEFT_WORD)

    override fun inflateDebit(data: DebitReceipt): PrinterDocument = with(data) {
        val doc = PrinterDocument()
        PrinterDocument(
            simple("Чек No.      $receiptNum"),
            simple("ИНН ${commonSettings.orgName}"),
            simple(commonSettings.orgName),
            divider,
            PrintableText(terminal.date),
            PrintableText("POS No. ${terminal.id}"),
            PrintableText("Терминал No. ${terminal.id}"),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableText("Карта ${card.type}: "),
            PrintableText(card.num),
            divider,
            PrintableDocumentItem(operationType, Format.CENTER),
            divider,
            PrintableText("Услуга     ${service.name}"),
            PrintableText("Кол-во   ${service.amountUnit}   ${service.amount}"),
            PrintableText("Цена за   ${service.priceUnit}   ${service.price}"),
            PrintableText("Итог   ${service.sumUnit}   ${service.sum}"),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableDocumentItem(operationType, Format.CENTER),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableText(responseCode.toString()),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableDocumentItem("Операция подтверждена вводов ПИН кода", Format.CENTER),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableText("Оператор No. $operatorNum"),
            PrintableDocumentItem("-", Format.DIVIDER),
            PrintableDocumentItem("Добро пожаловать", Format.CENTER),
        )
    }
}