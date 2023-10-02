package ru.petrolplus.pos.persitence.dto

/**
 * Данные о номере последнего напечатанного чека
 * @param id идентификатор записи, первичный ключ
 * @param receiptNumber Номер последнего напечатанного чека
 */
data class ReceiptParamsDTO(
    override val id: Int = 1,
    val receiptNumber: Long,
) : IdentifiableDTO
