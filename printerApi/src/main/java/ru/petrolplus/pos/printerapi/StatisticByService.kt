package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.persitence.dto.ServiceDTO

// TODO: удалить после появления БД
// временный класс для перечи статистики использования сервиса
data class StatisticByService(
    val service: ServiceDTO,
    val amountByNoRecalculatedTransaction: Long,
    val amountByRecalculatedTransaction: Long
) {
    val sumByNoRecalculatedTransaction: Long = amountByNoRecalculatedTransaction * service.price / 100
    val sumByRecalculatedTransaction: Long = amountByRecalculatedTransaction * service.price / 100
}