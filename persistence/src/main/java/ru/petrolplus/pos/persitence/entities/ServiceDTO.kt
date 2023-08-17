package ru.petrolplus.pos.persitence.entities

/**
 * Содержит информацию о доступных сервисах и услугах
 *
 *@param id Номер услуги, первичный ключ
 *@param name Название услуги (прим "АИ-95")
 *@param unit Единица измерения услуги (прим "Л" - литры)
 *@param price Цена услуги, представленная в целочисленном виде, с точностью 3 знака после запятой (43150 = 43.150р)
 */
data class ServiceDTO(
    override val id: Int,
    val name: String,
    val unit: String,
    val price: Long?,
) : IdentifiableDTO
