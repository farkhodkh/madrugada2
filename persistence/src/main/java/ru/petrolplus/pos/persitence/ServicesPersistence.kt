package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.entities.ServiceDB

/**
 * Интерфейс для доступа к сервисам и услугам
 */
interface ServicesPersistence {

    /**
     * метод для получения списка сервисов
     * @return список сервисов или услуг
     */
    suspend fun getAll(): List<ServiceDTO>

    /**
     * метод для добавления или замены сервиса или услуги
     * @param service сервис или услуга
     */
    suspend fun addOrReplace(service: ServiceDTO)
}

class ServicesPersistenceImpl(
    private val servicesDao: ServicesDao,
    private val mapper: Mapper<ServiceDTO, ServiceDB>,
) : ServicesPersistence {
    override suspend fun getAll(): List<ServiceDTO> {
        return servicesDao.getAll().map(mapper::toDTO)
    }

    override suspend fun addOrReplace(service: ServiceDTO) {
        servicesDao.insert(mapper.fromDTO(service))
    }

}