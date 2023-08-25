package ru.petroplus.pos.p7LibApi.dto

/**
 * класс обощающий все возможные варианты ответов от p7lib
 *
 * @author - @FAHA
 */
sealed class ResultCode(val code: Int)

/**
 * Успешный результат выполнения команды
 */
object OK: ResultCode(0)

/**
 * Параметры библиотеки уже были успешно проинициализированы
 */
object AlreadyInitialized : ResultCode(1)

/**
 * Библиотека не проинициализирована через P7Lib_Init()
 */
object NonInitializedError : ResultCode(2)

/**
 * Функция сброса карты в TP7Callbacks не проинициализирована
 */
object CardResetInitError : ResultCode(3)

/**
 * Функция ввода-вывода APDU-команд карты в TP7Callbacks не проинициализирована
 */
object CardIoInitError : ResultCode(4)

/**
 * Ошибка выбора апплета Petrol7 на карте
 */
object CardSelectError : ResultCode(5)

/**
 * Ошибка авторизации карты
 */
object CardAuthError : ResultCode(6)

/**
 * Ошибка загрузки параметров из Ini-файла
 */
object LoadIniError : ResultCode(7)

/**
 * Фатальная ошибка библиотеки, работа невозможна
 */
object LibFatalError : ResultCode(8)

/**
 * Нарушена последовательность выполнения команд библиотеки в сессии обслуживания
 */
object SequenceError : ResultCode(9)

/**
 * Карта, вставленная в ридер - не Petrol 7
 */
object NotPetrol7Card : ResultCode(10)

/**
 * Ошибка чтения данных с карты
 */
object CardReadError : ResultCode(11)

/**
 * Ошибка получения данных от SAM-карты
 */
object SamGetError : ResultCode(12)

/**
 * Некорректные данные PIN-блока
 */
object PinDataError : ResultCode(13)

/**
 * PIN не прошел проверку на карте
 */
object PinCheckError : ResultCode(14)

/**
 * Дебет закончился ошибкой
 */
object DebitError : ResultCode(15)

/**
 * Один из параметров (количество, цена или сумма) некорректен
 */
object ArgAmountPriceSumError : ResultCode(16)

/**
 * Параметр с номером услуги некорректен
 */
object ArgServiceError : ResultCode(17)

/**
 * Параметр содержащий значение PIN-блока некорректен
 */
object ArgPinblockError : ResultCode(18)

/**
 * Операция не поддерживается для карты J
 */
object ArgCardTypeJError : ResultCode(19)

/**
 * Возврат закончился ошибкой
 */
object RefundError : ResultCode(20)

/**
 * Функция сброса SAM в TP7Callbacks не проинициализирована
 */
object SamResetInitError : ResultCode(21)

/**
 * Функция ввода-вывода APDU-команд SAM в TP7Callbacks не проинициализирована
 */
object SamIoInitError : ResultCode(22)

/**
 * Ошибка выбора апплета InitSAM на карте
 */
object SamSelectError : ResultCode(23)

/**
 * Не прошла авторизацию SAM-карта (вынута или там что-то не то)
 */
object SamAuthInitError : ResultCode(24)

/**
 * Ошибка проверки SAM лицензии
 */
object SamLicenseError : ResultCode(25)

/**
 * Ошибка в сетевом модуле
 */
object NetworkModuleError : ResultCode(26)

/**
 * Неизвестная ошибка
 */
object UndefinedError : ResultCode(27)




