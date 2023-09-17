package ru.petrolplus.pos.p7LibApi

import ru.petrolplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer
import ru.petrolplus.pos.p7LibApi.responces.ResultCode
import ru.petrolplus.pos.p7LibApi.dto.PrintableDataDto
import ru.petrolplus.pos.p7LibApi.responces.OperationResult

/**
 * Interface для обслуживания "callback" ов библиотеки p7lib
 * @author - @FAHA
 */
interface IP7LibCallbacks {
    /**
     * Listener для обработки команд от p7lib
     */
    var listener: OnP7LibResultListener

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
     * @param answer - Ответ карты
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
     * @return Код результата выполнения операции
     */
    fun doASDataExchange(data: ByteArray): OperationResult

    /**
     * Метод поиска последней транзакции по номеру карты в базе данных
     * @param cardNumber - номер карты
     * @param record - <=> Запись в базе данных
     * @return Код результата выполнения операции
     */
    fun findLastTransactionDB(cardNumber: Long, record: TransactionRecordDto): ResultCode

    /**
     * Оповещение о завершении транзакции с передачей данных трензакции
     * для сохранения транзакции в БД
     * @param record - Данные транзакции для сохранения в БД
     * @return Код результата выполнения операции
     */
    fun completeTransactionDB(record: TransactionRecordDto): ResultCode

    /**
     * Распечатка чека с технической информацией и прочими данными
     * @param data - Данные для заполнения чека для печати
     * @return Код результата выполнения операции
     */
    fun printSimpleDoc(data: PrintableDataDto): ResultCode

    /**
     * Передача отложенных документов (OOB) на АС
     * @param oobData - Блок OOB в бинарном виде
     * @return Код результата выполнения операции
     */
    fun transferOOBToAS(oobData: ByteArray): ResultCode

}