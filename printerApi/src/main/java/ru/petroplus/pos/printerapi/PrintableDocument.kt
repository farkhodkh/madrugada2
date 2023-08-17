package ru.petroplus.pos.printerapi

import ru.petrolplus.pos.persitence.entities.TransactionDTO

sealed class PrintableDocument() {
    class Debit(val data: DocumentData): PrintableDocument()

    // TODO: добавить: возврат, отказ, сменный отчет
}