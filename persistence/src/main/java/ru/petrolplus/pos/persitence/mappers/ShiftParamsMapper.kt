package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.entities.ShiftParamsDTO
import ru.petrolplus.pos.room.entities.ShiftParamsDB

class ShiftParamsMapper : Mapper<ShiftParamsDTO, ShiftParamsDB> {
    override fun toDTO(input: ShiftParamsDB): ShiftParamsDTO = ShiftParamsDTO(
        id = input.id,
        currentShiftNumber = input.currentShiftNumber
    )

    override fun fromDTO(input: ShiftParamsDTO): ShiftParamsDB = ShiftParamsDB(
        id = input.id,
        currentShiftNumber = input.currentShiftNumber
    )

}