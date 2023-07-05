package ru.petroplus.pos.p7LibApi

import ru.petroplus.pos.p7LibApi.dto.*
import ru.petroplus.pos.p7LibApi.dto.card.CardInfo

/**
 * Interface описывает методы и апарметры для взаимодействия с библиотекой P7Lib (C++)
 * <=> - знак обозначает что параметр является "Выходным", должен быть передан пустым, без инициализации.
 * Заполнение будет происходить на стороне p7lib
 * @author - @FAHA
 */
interface IP7LibRepository {
    /**
     * Метод начальной инициализации библиотеки. Должен быть вызван сразу после запуска приложения,
     * до первых обращений к другим методоам
     * @param initData - класс содержащий данные полученные из файла настроек при настройки терминала (файл .ini)
     * @param lastOpGUID - идентификатор последней транзакции
     * @param callbacks - Класс для обслуживания callback ов
     * @param tempDir - адрес temp директории приложения
     * @param dataDir - адрес temp директории приложения
     * @return результат выполнения команды, ResultCode
     */
    fun init(
        initData: InitDataDto,
        lastOpGUID : TransactionUUIDDto,
        callbacks : IP7LibCallbacks,
        tempDir : String,
        dataDir : String,
    ): ResultCode

    /**
     * Метод завершения работы с библиотекой. Вызов метода обязателен
     */
    fun deInit(): ResultCode

    /**
     * Метод инициализации чтения карты
     * @param cardKey - <=> ключи для построения и шифрования PIN блока
     * @param cardData - <=> основная информация о карте
     * @return результат выполнения команды, ResultCode
     */
    fun detect(
        cardKey: CardKeyDto,
        cardData: CardInfo,
    ): ResultCode

    /**
     * Метод для debit операции
     * @param params - задаёт параметры для выполнения операции (код услуги, код кошелька, цену,
     * количество и стоимость проданной услуги, а также шифрованный PIN)
     * @param info - <=> используется для возвращения информации о выполненной транзакции, а также содержит информации, которую необходимо напечатать на чеке
     * @param transactionUuid - <=> используется для возвращения информации о идентификаторах выполненной операции
     * @return результат выполнения команды, ResultCode
     */
    fun debit(
        params: DebitParamsDto,
        info: TransactionInfoDto,
        transactionUuid: TransactionUUIDDto
    ): ResultCode

    /**
     * Метод для выполнения операции возврата покупки
     * @param params - задаёт параметры для выполнения операции (код купленной услуги, а также цену, количество и стоимость услуги)
     * @param info - <=> используется для возвращения информации о выполненной транзакции, а также содержит информации,
     * которую необходимо напечатать на чеке
     * @param transactionUuid - <=>используется для возвращения информации о идентификаторах выполненной операции
     * @return результат выполнения команды, ResultCode
     */
    fun refund(
        params: RefundParamsDto,
        info: TransactionInfoDto,
        transactionUuid: TransactionUUIDDto
    ): ResultCode

    /**
     * В случае возникновения ошибки при выпадении какой-либо из операций (решение принимаемся на основании возвращаемого
     * соответствующим методом значения типа TResultCode) информации о возникшей ошибке можно получить посредством вызова метода
     * @param errorInfo - <=> Класс содержит код ошибки, а также информацию об ошибке, которую следует напечатать на чеке
     * @return результат выполнения команды, ResultCode
     */
    fun getErrorInfo(errorInfo: ErrorInfoDto): ResultCode

    /**
     * Метод применяется для получения информации о библиотеке
     * @param libInfo - <=> Класс который содержит информацию о номере эсквайра и номер терминала, а также номер версии библиотеки
     * @return результат выполнения команды, ResultCode
     */
    fun getLibInfo(libInfo: LibInfoDto): ResultCode
}