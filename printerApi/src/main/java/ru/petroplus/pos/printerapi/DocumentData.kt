package ru.petroplus.pos.printerapi

import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import ru.petrolplus.pos.persitence.dto.ServiceDTO


// TODO: тестовый класс для объединения - удалить после появления Embedded
class DocumentData(
    var transaction: TransactionDTO,
    var service: ServiceDTO = ServiceDTO(0, "", "", 0),
    var commonSettings: CommonSettingsDTO,
)