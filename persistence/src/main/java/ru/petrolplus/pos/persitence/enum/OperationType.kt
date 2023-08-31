package ru.petrolplus.pos.persitence.enum

/**
 * Енумерация представляющая тип транзакции
 * @param id целочисленный идентификатор операции
 */
enum class OperationType(val id: Int) {
    DEBIT(1),
    CARD_CREDIT(2),
    ONLINE_REFILL(3),
    CARD_REFUND(4),
    ACCOUNT_REFUND(5);
}