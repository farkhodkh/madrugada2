package ru.petrolplus.pos.p7LibApi

import ru.petrolplus.pos.p7LibApi.dto.PrintDataDto
import ru.petrolplus.pos.p7LibApi.dto.ResultCode
import ru.petrolplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petrolplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer
import ru.petrolplus.pos.p7LibApi.responces.OperationResult
import java.io.File

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
    fun cardReset(answer: ApduAnswer): OperationResult

    /**
     * Обмен данными с картой
     * @param data - Информационное сообщение для передачи на карту
     * @param answer - Ответ APDU
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendDataToCard(data: ApduData, answer: ApduAnswer): OperationResult

    /**
     * Сброс SAM карты
     * @param answer - Ответ APDU
     * @return Код результат выполнения операции по сбросу карты
     */
    fun samReset(answer: ApduAnswer): OperationResult

    /**
     * Обмен данными с SAM картой
     * @param data - Информационное сообщение для передачи на карту
     * @return Код результат выполнения операции по сбросу карты
     */
    fun sendToSamCard(data: ApduData, answer: ApduAnswer): OperationResult

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
     */
    fun findLastTransaction(cardNumber: Int, transactionEntity: TransactionInfoDto): ResultCode

    /**
     * Обновление записей транзакции в БД
     * @param record - Содержание транзакции для печати
     */
    fun updateTransaction(record: TransactionRecordDto)

    /**
     * Распечатка чека по результатам транзакции
     * @param data - Данные для заполнения чека для печати
     */
    fun printSimpleDoc(data: PrintDataDto)

    /**
     *  Метод возвращает данные для передачи в шлюз
     *  @return OOB для передачи в шлюз
     */
    fun getPingData(): File
}