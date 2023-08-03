package ru.petrolplus.pos.persitence.entities

import java.util.Calendar

/**
 * Содержит данные об успешно выполненных операциях
 *
 * @param id GUID транзакции дебета/возврата, 32 ASCII_HEX символа, первичный ключ
 * @param acquirerId Номер эквайера (эмитент терминала)
 * @param terminalId Номер терминала
 * @param terminalDate Дата/время транзакции по времени терминала, храниться в базе в виде строки через конвертер типов
 * @param serviceIdWhat Вид топлива/услуги "за что платили" (в терминах эмитента карты)
 * @param serviceIdFrom Вид топлива/услуги "чем платили" (в терминах эмитента карты)
 * @param amount Кол-во услуги, представленная в целочисленном виде, с точностью 2 знака после запятой (250 = 2.5л)
 * @param price Цена услуги в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (10000 = 10.0р)
 * @param sum Сумма заказа в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (25000 = 25.0р)
 * @param operationCounter Номер операции (в терминах карты CARD_TRZ_COUNTER)
 * @param hasReturn Признак совершения успешного возврата по данному дебету (0 - нет, 1 - да)
 * @param crs32 CRC32 для данной записи, 4 байта
 * @param operationType Тип транзакции (0 - неизвестная операция, 1 - дебет, 2 - кредит кошелька,
 * 3 - онлайн-пополнение счета, 4 - возврат на карту, 5 - возврат на счет)
 * @param cardType Тип карты (0 - тип карты неизвестен, 1 - карта Петрол 5, 2 - Карта Петрол 7)
 * @param loyaltySum Сумма заказа в рублях с учетом скидки для карт Петрол5, представленная в целочисленном
 * виде, с точностью 3 знака после запятой (25000 = 25.0р), по-умолчанию равна SUM
 * @param deltaBonus Начисленные бонусы при транзакции с лояльностью в рублях, по-умолчанию равно 0
 * @param originalDebitTransactionId GUID ([id]) оригинальной транзакции дебета, 32 ASCII_HEX символа, заполняется для возврата
 * @param shiftNumber Номер смены, в которую была совершена транзакция
 * @param hasRecalculationTransaction Признак совершения пересчетной транзакции
 * (зависит от флага IsRecalcCard, от типа карты и от того, осуществлялось ли обслуживание
 * “Услуга за услугу“ (false - не пересчетное) или “Услуга за рубли“ (true- пересчетное))
 * @param rollbackCode Код для осуществления операции возврата (получен от карты во время дебета), 8 байт
 */

data class TransactionDTO(
    val id: String,
    val acquirerId: String,
    val terminalId: String,
    val terminalDate: Calendar,
    val serviceIdWhat: Int,
    val serviceIdFrom: Int,
    val amount: Int,
    val price: Long,
    val sum: Long,
    val operationCounter: Long,
    val hasReturn: Boolean,
    val crs32: String,
    val operationType: Int,
    val cardType: Int,
    val loyaltySum: Long,
    val deltaBonus: Long,
    val originalDebitTransactionId: String,
    val shiftNumber: Int,
    val hasRecalculationTransaction: Boolean,
    val rollbackCode: String
)
