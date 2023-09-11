package ru.petrolplus.pos.persitence.mappers

/**
 * Обобщающий интерфейс мапперов проекций в DTO,
 * где PRJ это проекция, а DTO соотвественно тип DTO
 */
interface ProjectionMapper<PRJ, DTO> {
    fun fromProjection(input: PRJ): DTO
}