package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * Данные об успешно выполненных операциях
 *
 * @param id GUID транзакции дебета/возврата, 32 ASCII_HEX символа, первичный ключ
 * @param acquirerId Номер эквайера (эмитент терминала)
 * @param terminalId Номер терминала
 * @param cardNumber Графический номер карты в терминах Петрол Плюс
 * @param operatorNumber Графический номер карты оператора в терминах Петрол Плюс
 * @param terminalDate Дата/время транзакции по времени терминала, храниться в базе в виде строки через конвертер типов
 * @param serviceIdWhat Вид топлива/услуги "за что платили" (в терминах эмитента карты)
 * @param serviceIdFrom Вид топлива/услуги "чем платили" (в терминах эмитента карты)
 * @param amount Кол-во услуги, представленная в целочисленном виде, с точностью 2 знака после запятой (250 = 2.5л)
 * @param price Цена услуги в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (10000 = 10.0р)
 * @param sum Сумма заказа в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (25000 = 25.0р)
 * @param cardTransactionCounter Номер операции (в терминах карты CARD_TRZ_COUNTER)
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
 * @param receiptNumber Номер чека
 * @param responseCode Результат выполнения операции (0 - успех, N - ошибка)
 */

@Entity(tableName = "transactions")
data class TransactionDB(
    @PrimaryKey
    @ColumnInfo("id")
    val id: String,

    @ColumnInfo("acquirer_id")
    val acquirerId: Int,

    @ColumnInfo("terminal_id")
    val terminalId: Int,

    @ColumnInfo("card_number")
    val cardNumber: String,

    @ColumnInfo("operator_number")
    val operatorNumber: String,

    @ColumnInfo("terminal_date")
    val terminalDate: Calendar,

    @ColumnInfo("service_id_what")
    val serviceIdWhat: Int,

    @ColumnInfo("service_id_from")
    val serviceIdFrom: Int,

    @ColumnInfo("amount")
    val amount: Long,

    @ColumnInfo("price")
    val price: Long,

    @ColumnInfo("sum")
    val sum: Long,

    @ColumnInfo("card_transaction_counter")
    val cardTransactionCounter: Long,

    @ColumnInfo("has_return")
    val hasReturn: Boolean,

    @ColumnInfo("crc_32")
    val crs32: String,

    @ColumnInfo("operation_type")
    val operationType: Int,

    @ColumnInfo("card_type")
    val cardType: Int,

    @ColumnInfo("loyalty_sum")
    val loyaltySum: Long,

    @ColumnInfo("delta_bonus")
    val deltaBonus: Long,

    @ColumnInfo("orig_debit_transaction_id")
    val originalDebitTransactionId: String,

    @ColumnInfo("shift_num")
    val shiftNumber: Int,

    @ColumnInfo("has_recalc_transaction")
    val hasRecalculationTransaction: Boolean,

    @ColumnInfo("rollback_code")
    val rollbackCode: String,

    @ColumnInfo("receipt_number")
    val receiptNumber: Long,

    @ColumnInfo("response_code")
    val responseCode: Int,
)
