package ru.petrolplus.pos.persitence.dto

/**
 * интерфейс метка о том что у сущности есть идентификатор
 */
interface IdentifiableDTO {
    /**
     * Определяет идентификатор сущности
     */
    val id: Int
}
