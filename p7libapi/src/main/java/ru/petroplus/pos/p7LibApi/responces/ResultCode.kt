package ru.petroplus.pos.p7LibApi.dto

/**
 * интерфейс обощающий все возможные варианты ответов от p7lib
 * TODO Нужны описания всех результатов ответа
 *
 * @author - @FAHA
 */
sealed interface ResultCode

/**
 * Успешный результат выполнения команды
 */
object OK : ResultCode

/**
 * Параметры библиотеки уже были успешно проинициализированы
 */
object AlreadyInitialized : ResultCode

/**
 *
 */
object NonInitializedError : ResultCode

/**
 *
 */
object CardResetInitError : ResultCode

/**
 *
 */
object CardIoInitError : ResultCode

/**
 *
 */
object CardSelectError : ResultCode

/**
 *
 */
object CardAuthError : ResultCode

/**
 *
 */
object LoadIniError : ResultCode

/**
 *
 */
object LibFatalError : ResultCode

/**
 *
 */
object SequenceError : ResultCode

/**
 *
 */
object NotPetrol7Card : ResultCode

/**
 *
 */
object CardReadError : ResultCode

/**
 *
 */
object SamGetError : ResultCode

/**
 *
 */
object PinDataError : ResultCode

/**
 *
 */
object PinCheckError : ResultCode

/**
 *
 */
object DebitError : ResultCode

/**
 *
 */
object ArgAmountPriceSumError : ResultCode

/**
 *
 */
object ArgServiceError : ResultCode

/**
 *
 */
object ArgPinBlockError : ResultCode

/**
 *
 */
object ArgCardTypeJError : ResultCode

/**
 *
 */
object RefundError : ResultCode

/**
 *
 */
object SamResetInitError : ResultCode

/**
 *
 */
object SamIoInitError : ResultCode

/**
 *
 */
object SamSelectError : ResultCode

/**
 *
 */
object SamAuthInitError : ResultCode

/**
 *
 */
object SamLicenseError : ResultCode

/**
 *
 */
object NetworkModuleError : ResultCode

/**
 *
 */
object UndefinedError : ResultCode




