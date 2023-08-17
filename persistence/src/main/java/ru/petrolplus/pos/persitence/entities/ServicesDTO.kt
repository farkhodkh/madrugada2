package ru.petrolplus.pos.persitence.entities

/**
 * Обертка для списка сервисов
 * @param services список номенклатуры (сервисы/услуги)
 */
class ServicesDTO(
    val services: List<ServiceDTO>
)