package ru.petrolplus.pos.persitence.dto

/**
 * Содержит информацию о доступных сервисах и услугах
 *
 *@param id Номер услуги, первичный ключ
 *@param name Название услуги (прим "АИ-95")
 *@param unit Единица измерения услуги (прим "Л" - литры)
 *@param price Цена услуги, представленная в целочисленном виде, с точностью 3 знака после запятой (43150 = 43.150р)
 * по умолчанию price 0, т.к возможен частичный испорт данных без цены. Необходимо это учесть в UI слое
 */
data class ServiceDTO(
    override val id: Int,
    val name: String,
    val unit: String,
    //FIXME важно!! Проверять в UI слое то что оператор заполнил цену!!
    val price: Long = 0,
) : IdentifiableDTO
