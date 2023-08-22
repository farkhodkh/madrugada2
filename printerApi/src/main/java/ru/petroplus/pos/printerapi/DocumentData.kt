package ru.petroplus.pos.printerapi

import ru.petrolplus.pos.persitence.entities.CommonSettingsDTO
import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petrolplus.pos.persitence.entities.TransactionDTO

class DocumentData(
    var transaction: TransactionDTO,
    var service: ServiceDTO? = null,
    var commonSettings: CommonSettingsDTO,
)