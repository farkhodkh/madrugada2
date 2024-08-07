package ru.petrolplus.pos.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Данные о доступных сервисах и услугах
 *
 *@param id Номер услуги, первичный ключ
 *@param name Название услуги (прим "АИ-95")
 *@param unit Единица измерения услуги (прим "Л" - литры)
 *@param price Цена услуги, представленная в целочисленном виде, с точностью 3 знака после запятой (43150 = 43.150р)
 * по умолчанию price 0, т.к заполяется отдельно оператором.
 *
 */
@Entity(tableName = "services")
data class ServiceDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("unit")
    val unit: String,

    //FIXME важно!! Проверять в UI слое то что оператор заполнил цену!!
    @ColumnInfo("price")
    val price: Long = 0,
)
