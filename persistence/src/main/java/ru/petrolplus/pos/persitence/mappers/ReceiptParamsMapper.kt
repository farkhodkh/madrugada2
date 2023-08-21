package ru.petrolplus.pos.persitence.mappers

import ru.petrolplus.pos.persitence.entities.ReceiptParamsDTO
import ru.petrolplus.pos.room.entities.ReceiptParamsDB

class ReceiptParamsMapper : Mapper<ReceiptParamsDTO, ReceiptParamsDB> {
    override fun toDTO(input: ReceiptParamsDB): ReceiptParamsDTO = ReceiptParamsDTO(
        id = input.id,
        receiptNumber = input.receiptNumber
    )

    override fun fromDTO(input: ReceiptParamsDTO): ReceiptParamsDB = ReceiptParamsDB(
        id = input.id,
        receiptNumber = input.receiptNumber
    )
}