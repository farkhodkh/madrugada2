package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.petrolplus.pos.room.projections.ReceiptProjection

/**
 * Data Access Object для получения различных данных о чеках
 */
@Dao
interface ReceiptDao {

    /**
     * Комбинированный запрос из 4х таблиц (transactions, receipt_params, common_settings и services)
     * ищент по id определенную транзакцию, забирает из таблиц common_settings и receipt_number единственные
     * значения и ищет данные сервиса по id соответствующему service_id_what из транзакции
     * @param transactionId идентификатор транзакции
     * @return проекцию из TransactionDB, ServiceDB, CommonSettingsDB и ReceiptParamsDB, может быть
     * null если не найдено транзакций с таким id
     */
    @Query("SELECT * FROM (SELECT * FROM transactions WHERE id = :transactionId LIMIT 1) " +
            "CROSS JOIN (SELECT " +
                "id as r_id, " +
                "current_receipt_number as r_current_receipt_number FROM receipt_params LIMIT 1) " +
            "CROSS JOIN (SELECT " +
                "id as c_id, " +
                "organization_name as c_organization_name, " +
                "organization_inn as c_organization_inn, " +
                "pos_name as c_pos_name FROM common_settings LIMIT 1) " +
            "LEFT JOIN (SELECT " +
                "id as s_id, " +
                "name as s_name, " +
                "unit as s_unit, " +
                "price as s_price from services) ON service_id_what = s_id"
    )
    fun getDebitReceiptByTransactionId(transactionId: String): ReceiptProjection?
}