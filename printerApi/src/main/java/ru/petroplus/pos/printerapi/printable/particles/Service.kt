package ru.petroplus.pos.printerapi.printable.particles

data class Service(
    val name: String,
    val amount: String,
    val amountUnit: String,
    val price: String,
    val sum: String,
    val priceUnit: String,
    val sumUnit: String = priceUnit,
)