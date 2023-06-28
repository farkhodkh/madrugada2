package ru.petroplus.pos.p7LibApi.dto

/**
 * интерфейс обощающий все возможные варианты ответов от p7lib
 * TODO Нужны описания всех результатов ответа
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
 *
 */
object NonInitializedError : ResultCode(2)

/**
 *
 */
object CardResetInitError : ResultCode(3)

/**
 *
 */
object CardIoInitError : ResultCode(4)

/**
 *
 */
object CardSelectError : ResultCode(5)

/**
 *
 */
object CardAuthError : ResultCode(6)

/**
 *
 */
object LoadIniError : ResultCode(7)

/**
 *
 */
object LibFatalError : ResultCode(8)

/**
 *
 */
object SequenceError : ResultCode(9)

/**
 *
 */
object NotPetrol7Card : ResultCode(10)

/**
 *
 */
object CardReadError : ResultCode(11)

/**
 *
 */
object SamGetError : ResultCode(12)

/**
 *
 */
object PinDataError : ResultCode(13)

/**
 *
 */
object PinCheckError : ResultCode(14)

/**
 *
 */
object DebitError : ResultCode(15)

/**
 *
 */
object ArgAmountPriceSumError : ResultCode(16)

/**
 *
 */
object ArgServiceError : ResultCode(17)

/**
 *
 */
object ArgPinBlockError : ResultCode(18)

/**
 *
 */
object ArgCardTypeJError : ResultCode(19)

/**
 *
 */
object RefundError : ResultCode(20)

/**
 *
 */
object SamResetInitError : ResultCode(21)

/**
 *
 */
object SamIoInitError : ResultCode(22)

/**
 *
 */
object SamSelectError : ResultCode(23)

/**
 *
 */
object SamAuthInitError : ResultCode(24)

/**
 *
 */
object SamLicenseError : ResultCode(25)

/**
 *
 */
object NetworkModuleError : ResultCode(26)

/**
 *
 */
object UndefinedError : ResultCode(27)




