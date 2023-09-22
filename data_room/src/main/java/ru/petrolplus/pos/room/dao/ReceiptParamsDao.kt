package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import ru.petrolplus.pos.room.entities.ReceiptParamsDB

/**
 * Data Access Object для получения и сохранения параметров последнего напечатанного чека
 */
@Dao
interface ReceiptParamsDao : BaseDao<ReceiptParamsDB>