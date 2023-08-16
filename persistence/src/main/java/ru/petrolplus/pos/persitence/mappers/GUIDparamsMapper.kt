package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.entities.GUIDParamsDTO
import ru.petrolplus.pos.room.entities.GUIDParamsDB

class GUIDparamsMapper : Mapper<GUIDParamsDTO, GUIDParamsDB> {
    override fun toDTO(input: GUIDParamsDB): GUIDParamsDTO = GUIDParamsDTO(
        id = input.id,
        lastOnlineTransaction = input.lastOnlineTransaction,
        lastGeneratedTime = input.lastGeneratedTime,
        clockSequence = input.clockSequence,
        hasNodeId = input.hasNodeId,
        nodeId = input.nodeId
    )

    override fun fromDTO(input: GUIDParamsDTO): GUIDParamsDB = GUIDParamsDB(
        id = input.id,
        lastOnlineTransaction = input.lastOnlineTransaction,
        lastGeneratedTime = input.lastGeneratedTime,
        clockSequence = input.clockSequence,
        hasNodeId = input.hasNodeId,
        nodeId = input.nodeId
    )

}