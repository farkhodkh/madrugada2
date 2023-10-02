package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents
import ru.petrolplus.pos.printerapi.ResponseCode

internal fun ResponseCode.toUi(): IPrintable = GeneralComponents.centredText(description)
