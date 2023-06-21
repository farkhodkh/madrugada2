package ru.petroplus.pos.p7LibApi

import ru.petroplus.pos.p7LibApi.dto.ResultCode
import ru.petroplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petroplus.pos.p7LibApi.requests.ApduData
import ru.petroplus.pos.p7LibApi.responces.OperationResult
import java.util.concurrent.TimeUnit

/**
 * Interface для обслуживания "callback" ов библиотеки p7lib
 * @author - @FAHA
 */
interface IP7LibCallbacks {
    /**
     * Метод для вывода лог сообщений
     * @param message - текстовое сообщение полученное от p7lib
     */
    fun log(message: String)

    /**
     * Метод "Сброса карты", возвращает код результата операции
     * @return Код результат выполнения операции по сбросу карты
     */
    fun cardReset(): OperationResult

    /**
     * Обмен данными с картой
     * @param data - Информационное сообщение для передачи на карту
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendDataToCard(data: ApduData): OperationResult

    /**
     * Сброс SAM карты
     * @return Код результат выполнения операции по сбросу карты
     */
    fun samReset(): OperationResult

    /**
     * Обмен данными с SAM картой
     * @param data - Информационное сообщение для передачи на карту
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendToSamCard(data: ApduData): OperationResult

    /**
     * Проверка наличия установленного соединения с "АС"
     * @param timeUnit - таймаут в миллисекундах
     * @return Результат соединениия с АС
     */
    fun connectToAS(timeUnit: Long): Boolean

    /**
     * Передача данных в АС
     * @param data - бинарное нешифрованное информационное сообщение для передачи в АС
     * @return Код результат выполнения операции
     */
    fun sendToAS(data: ByteArray): OperationResult

    /**
     * Метод поиска последней транзакции по номеру карты
     * @param cardNumber - номер карты
     * @param transactionEntity - <=> Запись в базе данных
     * TODO - Нужно согласовать схему таблицы записей
     */
    fun findLastTransaction(cardNumber: Int, transactionEntity: TransactionInfoDto): ResultCode

    /**
     *
     */
//    fun printSimpleDoc(SimpleDoc)
}