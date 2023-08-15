package ru.petroplus.pos.printerapi.printable.documents

import ru.petroplus.pos.printerapi.printable.particles.Card
import ru.petroplus.pos.printerapi.printable.particles.CommonSettings
import ru.petroplus.pos.printerapi.printable.particles.Service
import ru.petroplus.pos.printerapi.printable.particles.Terminal

data class DebitReceipt(
    val operatorNum: String,
    val receiptNum: Int,
    val commonSettings: CommonSettings,
    val terminal: Terminal,
    val card: Card,
    val operationType: String,
    val service: Service,
    val responseCode: Int,
) : Printable

