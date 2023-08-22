package ru.petrolplus.pos.persitence.dto

import java.util.Calendar

/**
 * Содержит данные необходимые для печати чека дебета
 *
 *@param receiptNumber текущий номер чека
 *@param organizationName название организации
 *@param organizationInn ИНН организации
 *@param posName Название ТО/АЗС
 *@param terminalDate Дата/время транзакции по времени терминала
 *@param terminalId Номер терминала
 *@param cardType Тип карты (0 - тип карты неизвестен, 1 - карта Петрол 5, 2 - Карта Петрол 7)
 *@param cardNumber Графический номер карты в терминах Петрол Плюс
 *@param operationType Тип транзакции (0 - неизвестная операция, 1 - дебет, 2 - кредит кошелька,
 * 3 - онлайн-пополнение счета, 4 - возврат на карту, 5 - возврат на счет)
 *@param serviceName Название услуги (прим "АИ-95")
 *@param serviceUnit Единица измерения услуги (прим "Л" - литры)
 *@param price Цена услуги в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (10000 = 10.0р)
 *@param amount Кол-во услуги, представленная в целочисленном виде, с точностью 2 знака после запятой (250 = 2.5л)
 *@param sum Сумма заказа в рублях, представленная в целочисленном виде, с точностью 3 знака после запятой (25000 = 25.0р)
 *@param responseCode Результат выполнения операции (0 - успех, N - ошибка)
 */
data class DebitReceiptDTO(
    val receiptNumber: Long,
    val organizationName: String,
    val organizationInn: String,
    val posName: String,
    val terminalDate: Calendar,
    val terminalId: Int,
    val cardType: Int,
    val cardNumber: String,
    val operationType: Int,
    val serviceName: String,
    val serviceUnit: String,
    val amount: Long,
    val price: Long,
    val sum: Long,
    val responseCode: Int
)
