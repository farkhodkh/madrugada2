package ru.petroplus.pos.p7LibApi

import ru.petroplus.pos.p7LibApi.dto.PrintDataDto
import ru.petroplus.pos.p7LibApi.dto.ResultCode
import ru.petroplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petroplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petroplus.pos.p7LibApi.requests.ApduData
import ru.petroplus.pos.p7LibApi.responces.ApduAnswer
import ru.petroplus.pos.p7LibApi.responces.OperationResult

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
     * @param answer - Ответ APDU
     * @return Код результат выполнения операции по сбросу карты
     */
    fun cardReset(answer: ApduAnswer): ResultCode

    /**
     * Обмен данными с картой
     * @param data - Информационное сообщение для передачи на карту
     * @param answer - Ответ APDU
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendDataToCard(data: ApduData, answer: ApduAnswer): ResultCode

    /**
     * Сброс SAM карты
     * @param answer - Ответ APDU
     * @return Код результат выполнения операции по сбросу карты
     */
    fun samReset(answer: ApduAnswer): ResultCode

    /**
     * Обмен данными с SAM картой
     * @param data - Информационное сообщение для передачи на карту
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendDataToSam(data: ApduData, answer: ApduAnswer): ResultCode

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
    fun doASDataExchange(data: ByteArray): OperationResult

    /**
     * Метод поиска последней транзакции по номеру карты
     * @param cardNumber - номер карты
     * @param transactionEntity - <=> Запись в базе данных
     */
    fun findLastTransactionDB(cardNumber: Int, record: TransactionRecordDto): ResultCode

    /**
     * Обновление записей транзакции в БД
     * @param record - Содержание транзакции для печати
     */
    fun completeTransactionDB(record: TransactionRecordDto): ResultCode

    /**
     * Распечатка чека по результатам транзакции
     * @param data - Данные для заполнения чека для печати
     */
    fun printSimpleDoc(data: PrintDataDto): ResultCode
}