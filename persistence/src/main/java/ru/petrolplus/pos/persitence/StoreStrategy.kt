package ru.petrolplus.pos.persitence

import ru.petrolplus.pos.persitence.entities.IdentifiableDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.BaseDao
import java.lang.IllegalArgumentException
import kotlin.jvm.Throws

/**
 * Интерфейс для стратегии сохранения в базу определенных элементов,
 */
interface StoreStrategy {

    /**
     * Метод для сохранения записи
     * DTO - любой тип DTO который расширяет IdentifiebleDTO, ST - сущность бд (по сути таблица в которыю мы кладем запись)
     */
    suspend fun <DTO : IdentifiableDTO, ST>store(dao: BaseDao<ST>, mapper: Mapper<DTO, ST>, entity: DTO)
}

class DatabaseStoreStrategy : StoreStrategy {

    /**
     * Реализация метода сохранения записи в бд, проверяет идентификатор записи и далее маппит из DTO модели в модель хранилища
     * @param dao любой наследник базового DAO
     * @param mapper реализация маппера подходящего для типо DTO и ST
     * @param entity сохраняемая в базу сущности
     * @throws IllegalArgumentException в случае если идентификатор сохраняемой записи не равен 1
     */
    @Throws(IllegalArgumentException::class)
    override suspend fun <DTO : IdentifiableDTO, ST> store(dao: BaseDao<ST>, mapper: Mapper<DTO, ST>, entity: DTO) {
        //TODO перести текст в строковые ресурсы после слияния с веткое где есть утилиты для получения строк без прямого доступа к context
        require(entity.id == 1) { "Id настройки должен равнятья единице, т.к храним всего лишь одну запись в таблице" }
        dao.insert(mapper.fromDTO(entity))
    }
}