package ru.petrolplus.pos.persitence.entities

/**
 * интерфейс метка о том что у сущности есть идентификатор
 */
interface IdentifiableDTO {
    /**
     * Определяет идентификатор сущности
     */
    val id: Int
}
