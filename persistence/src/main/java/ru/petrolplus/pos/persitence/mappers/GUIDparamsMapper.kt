package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.entities.GUIDparamsDTO
import ru.petrolplus.pos.room.entities.GUIDparamsDB

class GUIDparamsMapper : Mapper<GUIDparamsDTO, GUIDparamsDB> {
    override fun toDTO(input: GUIDparamsDB): GUIDparamsDTO = GUIDparamsDTO(
        id = input.id,
        lastOnlineTransaction = input.lastOnlineTransaction,
        lastGeneratedTime = input.lastGeneratedTime,
        clockSequence = input.clockSequence,
        hasNodeId = input.hasNodeId,
        nodeId = input.nodeId
    )

    override fun fromDTO(input: GUIDparamsDTO): GUIDparamsDB = GUIDparamsDB(
        id = input.id,
        lastOnlineTransaction = input.lastOnlineTransaction,
        lastGeneratedTime = input.lastGeneratedTime,
        clockSequence = input.clockSequence,
        hasNodeId = input.hasNodeId,
        nodeId = input.nodeId
    )

}