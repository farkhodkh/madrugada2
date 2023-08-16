package ru.petroplus.pos.printerapi.printable.documents

import ru.petrolplus.pos.persitence.entities.TransactionDTO

sealed class PrintableReceipt() {
    class Debit(val data: TransactionDTO): PrintableReceipt()

    // TODO: добавить: возврат, отказ, сменный отчет
}