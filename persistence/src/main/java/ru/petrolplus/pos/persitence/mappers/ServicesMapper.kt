package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.dto.ServiceDTO
import ru.petrolplus.pos.room.entities.ServiceDB

class ServicesMapper : Mapper<ServiceDTO, ServiceDB> {
    override fun toDTO(input: ServiceDB): ServiceDTO = ServiceDTO(
        id = input.id,
        name = input.name,
        unit = input.unit,
        price = input.price
    )

    override fun fromDTO(input: ServiceDTO): ServiceDB = ServiceDB(
        id = input.id,
        name = input.name,
        unit = input.unit,
        price = input.price
    )

}