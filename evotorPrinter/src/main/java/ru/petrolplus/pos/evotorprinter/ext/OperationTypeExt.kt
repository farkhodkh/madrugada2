package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import ru.petrolplus.pos.printerapi.ResponseCode

internal fun OperationType.toUi(responseCode: ResponseCode): Array<IPrintable> {
    val descriptionsList = mutableListOf(centredText(responseCode.description))
    if (responseCode != ResponseCode.Success) return descriptionsList.toTypedArray()

    if (this == OperationType.DEBIT) {
        descriptionsList.add(centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_PIN))
        descriptionsList.add(centredText(IntroductoryConstruction.DEBIT_CONFIRMED_BY_PIN))
    } else if (
        this == OperationType.CARD_REFUND
        || this == OperationType.ACCOUNT_REFUND
        || this == OperationType.ONLINE_REFILL
        ) {
        descriptionsList.add(centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_TERMINAL))
        descriptionsList.add(centredText(IntroductoryConstruction.RETURN_CONFIRMED_BY_TERMINAL))
    }
    return descriptionsList.toTypedArray()
}