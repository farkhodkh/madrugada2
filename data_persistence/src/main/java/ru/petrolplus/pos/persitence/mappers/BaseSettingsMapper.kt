package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO
import ru.petrolplus.pos.room.entities.BaseSettingsDB

class BaseSettingsMapper : Mapper<BaseSettingsDTO, BaseSettingsDB> {
    override fun toDTO(input: BaseSettingsDB): BaseSettingsDTO = BaseSettingsDTO(
        id = input.id,
        acquirerId = input.acquirerId,
        terminalId = input.terminalId,
        hostPort = input.hostPort,
        hostIp = input.hostIp,
    )

    override fun fromDTO(input: BaseSettingsDTO): BaseSettingsDB = BaseSettingsDB(
        id = input.id,
        acquirerId = input.acquirerId,
        terminalId = input.terminalId,
        hostPort = input.hostPort,
        hostIp = input.hostIp,
    )
}
