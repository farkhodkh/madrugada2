package ru.petrolplus.pos.persitence.mappers

/**
 * Обобщающий интерфейс для мапперов в DTO и обратно
 * где DTO это тип DTO, а ST это тип в котором производится хранение (сущность бд и так далее)
 */
interface Mapper<DTO, ST> {
    fun toDTO(input: ST): DTO
    fun fromDTO(input: DTO): ST
}
