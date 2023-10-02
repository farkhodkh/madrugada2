package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import ru.petrolplus.pos.room.entities.CommonSettingsDB

class CommonSettingsMapper : Mapper<CommonSettingsDTO, CommonSettingsDB> {
    override fun toDTO(input: CommonSettingsDB): CommonSettingsDTO = CommonSettingsDTO(
        id = input.id,
        organizationName = input.organizationName,
        organizationInn = input.organizationInn,
        posName = input.posName,
    )

    override fun fromDTO(input: CommonSettingsDTO): CommonSettingsDB = CommonSettingsDB(
        id = input.id,
        organizationName = input.organizationName,
        organizationInn = input.organizationInn,
        posName = input.posName,
    )
}
