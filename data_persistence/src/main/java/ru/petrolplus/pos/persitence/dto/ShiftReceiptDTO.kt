package ru.petrolplus.pos.persitence.dto

import java.util.Calendar

/**
 * Содержить информацию об услуге, а также общее количество и сумму предоставленных за период.
 * @param serviceName название услуги
 * @param serviceUnit единица измерения услуги
 * @param servicePrice цена за услугу
 * @param totalAmount суммарное количество предоставленных услуг за период
 * @param totalSum сумма всех предоставленных услуг за период
 * @param totalRecalculationAmount суммарное количество предоставленных услуг за период по пересчетным операциям
 * @param totalRecalculationSum сумма всех углуг по пересчетным операциям
 */
data class ServiceTotalDTO(
    val serviceName: String,
    val serviceUnit: String,
    val servicePrice: Long,
    val totalAmount: Long,
    val totalSum: Long,
    val totalRecalculationAmount: Long,
    val totalRecalculationSum: Long,
)

/**
 * Содержит отчет за смену
 * @param shiftNumber текущий номер смены
 * @param currentShiftStart дата старта смены
 * @param organizationName наименование организации
 * @param organizationInn ИНН организации
 * @param posName название ТО/АЭС
 * @param terminalId номер терминала
 * @param debits список услуг с суммарной информацией (количество, сумма) по ним. Фильтруется по операциям дебета
 * @param cardRefunds список услуг с суммарной информацией (количество, сумма) по ним. Фильтруется по возратам на карту
 * @param accountRefunds список услуг с суммарной информацией (количество, сумма) по ним. Фильтруется по возратам на счет
 * @param operatorNumb номер оператора в смене, берется из транзакций
 * @param totalDebitsOperations суммарное количество дебетов
 * @param totalRefundsOperations суммарное количество возвратов
 * @param totalOperations суммарное количество операций
 * @param totalSum итоговая сумма по всем операциям (сумма всех дебетов - сумма всех возвратов)
 */
data class ShiftReceiptDTO(
    val shiftNumber: Int,
    val currentShiftStart: Calendar,
    val organizationName: String,
    val organizationInn: String,
    val terminalId: Int,
    val posName: String,
    val debits: List<ServiceTotalDTO>,
    val cardRefunds: List<ServiceTotalDTO>,
    val accountRefunds: List<ServiceTotalDTO>,
    val operatorNumb: Int,
    val totalDebitsOperations: Int,
    val totalRefundsOperations: Int,
    val totalOperations: Int,
    val totalSum: Long,
)
