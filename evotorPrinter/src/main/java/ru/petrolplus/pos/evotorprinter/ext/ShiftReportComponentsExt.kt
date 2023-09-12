package ru.petrolplus.pos.evotorprinter.ext

import ru.evotor.devices.commons.printer.PrinterDocument
import ru.petrolplus.pos.evotorprinter.GeneralComponents
import ru.petrolplus.pos.evotorprinter.GeneralComponents.dashDivider
import ru.petrolplus.pos.evotorprinter.ShiftReportComponents
import ru.petrolplus.pos.evotorprinter.TerminalComponents
import ru.petrolplus.pos.persitence.dto.ShiftReceiptDTO
import ru.petrolplus.pos.printerapi.IntroductoryConstruction
import java.util.Date

fun ShiftReceiptDTO.toUi(currentDate: Date, paperWidth: Int) = PrinterDocument(
    GeneralComponents.centredText(IntroductoryConstruction.SHIFT_REPORT_TITLE),
    *GeneralComponents.getOrganizationData(organizationName, posName, organizationInn),
    *ShiftReportComponents.getShiftData(currentShiftStart.time, currentDate, paperWidth),
    *TerminalComponents.getTerminalDate(terminalId, paperWidth),
    *ShiftReportComponents.getOperationsByPetrolPlus(this, dashDivider, paperWidth),
    GeneralComponents.getOperatorData(operatorNumb.toString(), paperWidth)
)