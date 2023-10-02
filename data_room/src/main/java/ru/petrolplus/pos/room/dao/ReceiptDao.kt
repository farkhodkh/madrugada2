package ru.petrolplus.pos.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import ru.petrolplus.pos.room.projections.CardsTotalProjection
import ru.petrolplus.pos.room.projections.ReceiptProjection
import ru.petrolplus.pos.room.projections.ShiftReceiptHeaderProjection
import ru.petrolplus.pos.room.projections.TransactionByServiceProjection

/**
 * Data Access Object для получения различных данных о чеках
 */
@Dao
interface ReceiptDao {

    /**
     * Комбинированный запрос из 4х таблиц (transactions, receipt_params, common_settings и services)
     * ищет по id определенную транзакцию ограничивает поиск только одним результатом. Создает фейковую колонку
     * со значением 1 для внешних связей через механизм OneToMany room см [ReceiptProjection]
     */
    @Transaction
    @Query("SELECT *, 1 as relation_id FROM transactions WHERE id = :transactionId LIMIT 1")
    fun getDebitReceiptByTransactionId(transactionId: String): ReceiptProjection?

    /**
     * Метод для получения суммарных данных (общее количество и сумма предоставленных услуг, а так же
     * общее количество и сумма услуг пересчетных транзакций) по типу операции (дебет, возврат) и номеру смены
     * @param operationType целочисленное представление типа услуги [ru.petrolplus.pos.room.entities.TransactionDB.operationType]
     * @param shiftNumber номер текущий смены [ru.petrolplus.pos.room.entities.ShiftParamsDB.currentShiftNumber]
     * @return возвращает проекцию со свойствами в которые сохраняются рассчитаные запросом данные,
     * коллекция может быть пустой если не найдено ни одной транзакции
     */
    @Query(
        "SELECT " +
            "SUM(amount) AS total_amount, " +
            "SUM(sum) AS total_sum, " +
            "SUM(CASE WHEN has_recalc_transaction = 1 THEN amount ELSE 0 END) AS total_recalc_amount, " +
            "SUM(CASE WHEN has_recalc_transaction = 1 THEN sum ELSE 0 END) AS total_recalc_sum, " +
            "services.* " +
            "FROM transactions LEFT JOIN services ON service_id_what = services.id WHERE operation_type = :operationType AND shift_num = :shiftNumber GROUP BY services.id ",
    )
    fun getReportByOperationTypeForShift(operationType: Int, shiftNumber: Int): List<TransactionByServiceProjection>

    /**
     * Метод для получения текущий параметров смены и общих настроек терминала
     * часть запроса выполняет Room, используя relation_id с помощью механизма one to many
     * он достает данные для общих настроек
     * @return проекцию содержащую параметры смены и общие настройки
     */
    @Transaction
    @Query("SELECT *, 1 as relation_id, transactions.operator_number, transactions.terminal_id FROM shift_params LEFT JOIN transactions ON current_shift_number = shift_num LIMIT 1")
    fun getShiftInfo(): ShiftReceiptHeaderProjection

    /**
     * Метод для получения общего количества операций за смену (дебет, возврат, дебет + возврат)
     * @param shiftNumber номер текущей смены [ru.petrolplus.pos.room.entities.ShiftParamsDB.currentShiftNumber]
     */
    @Query(
        "SELECT " +
            "COUNT(DISTINCT CASE WHEN operation_type = 1 THEN id END) as total_debits, " +
            "COUNT(DISTINCT CASE WHEN operation_type IN (4, 5) THEN id END) AS total_refunds, " +
            "COUNT(DISTINCT CASE WHEN operation_type IN (1, 4, 5) THEN id END) AS total_operations, " +
            "SUM(CASE WHEN operation_type = 1 THEN sum ELSE 0 END) - SUM(CASE WHEN operation_type IN (4,5) THEN sum ELSE 0 END) as total_sum " +
            "FROM transactions WHERE shift_num = :shiftNumber",
    )
    fun getCardsTotal(shiftNumber: Int): CardsTotalProjection
}
