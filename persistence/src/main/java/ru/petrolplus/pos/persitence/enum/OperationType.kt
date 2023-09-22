package ru.petrolplus.pos.persitence.enum

import ru.petrolplus.pos.R
import ru.petrolplus.pos.resources.ResourceHelper

/**
 * Энумерация представляющая тип транзакции
 * @param id целочисленный идентификатор операции
 * @param description текстовое описание операции
 */
enum class OperationType(val id: Int, val description: String) {
    UNKNOWN(0, ResourceHelper.getStringResource(R.string.unknown_operation)),
    DEBIT(1, ResourceHelper.getStringResource(R.string.debit)),
    WALLET_CREDIT(2, ResourceHelper.getStringResource(R.string.wallet_credit)),
    ONLINE_REFILL(3, ResourceHelper.getStringResource(R.string.online_refill)),
    CARD_REFUND(4, ResourceHelper.getStringResource(R.string.refund_to_card)),
    ACCOUNT_REFUND(5, ResourceHelper.getStringResource(R.string.refund_to_account)),
}