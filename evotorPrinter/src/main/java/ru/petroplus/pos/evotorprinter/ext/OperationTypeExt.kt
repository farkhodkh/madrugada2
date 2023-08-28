package ru.petroplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petroplus.pos.evotorprinter.GeneralComponents.centredText
import ru.petroplus.pos.printerapi.IntroductoryConstruction
import ru.petroplus.pos.printerapi.OperationType
import ru.petroplus.pos.printerapi.ResponseCode

internal fun OperationType.toUi(responseCode: ResponseCode): Array<IPrintable> {
    val descriptionsList = mutableListOf(centredText(responseCode.description))
    if (responseCode != ResponseCode.Success) return descriptionsList.toTypedArray()

    if (this == OperationType.Debit) {
        descriptionsList.add(centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_PIN))
        descriptionsList.add(centredText(IntroductoryConstruction.DEBIT_CONFIRMED_BY_PIN))
    } else if (this is OperationType.Return) {
        descriptionsList.add(centredText(IntroductoryConstruction.OPERATION_CONFIRMED_BY_TERMINAL))
        descriptionsList.add(centredText(IntroductoryConstruction.RETURN_CONFIRMED_BY_TERMINAL))
    }
    return descriptionsList.toTypedArray()
}