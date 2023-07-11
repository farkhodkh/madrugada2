package ru.petroplus.pos.p7LibApi.dto

//Является полным аналогом TP7ErrorType P7Lib c++
enum class ResultCode(val Code: Int) {
    OK(0),                             //!< Ошибок нет, успех
    AlreadyInitialized(1),             //!< Библиотека уже инициализировалась через P7Lib_Init()
    NonInitializedError(2),            //!< Библиотека не проинициализирована через P7Lib_Init()
    CardResetInitError(3),             //!< Функция сброса карты в TP7Callbacks не проинициализирована
    CardIoInitError(4),                //!< Функция ввода-вывода APDU-команд карты в TP7Callbacks не проинициализирована
    CardSelectError(5),                //!< Ошибка выбора апплета Petrol7 на карте
    CardAuthError(6),                  //!< Ошибка авторизации карты
    LoadIniError(7),                   //!< Ошибка загрузки параметров из Ini-файла
    LibFatalError(8),                  //!< Фатальная ошибка библиотеки, работа невозможна
    SequenceError(9),                  //!< Нарушена последовательность выполнения команд библиотеки в сессии обслуживания
    NotPetrol7Card(10),                //!< Карта, вставленная в ридер - не Petrol 7
    CardReadError(11),                 //!< Ошибка чтения данных с карты
    SamGetError(12),                   //!< Ошибка получения данных от SAM-карты
    PinDataError(13),                  //!< Некорректные данные PIN-блока
    PinCheckError(14),                 //!< PIN не прошел проверку на карте
    DebitError(15),                    //!< Дебет закончился ошибкой
    ArgAmountPriceSumError(16),        //!< Один из параметров (количество, цена или сумма) некорректен
    ArgServiceError(17),               //!< Параметр с номером услуги некорректен
    ArgPinblockError(18),              //!< Параметр содержащий значение PIN-блока некорректен
    ArgCardTypeJError(19),             //!< Операция не поддерживается для карты J
    RefundError(20),                   //!< Возврат закончился ошибкой
    SamResetInitError(21),             //!< Функция сброса SAM в TP7Callbacks не проинициализирована
    SamIoInitError(22),                //!< Функция ввода-вывода APDU-команд SAM в TP7Callbacks не проинициализирована
    SamSelectError(23),                //!< Ошибка выбора апплета InitSAM на карте
    SamAuthInitError(24),              //!< Не прошла авторизацию SAM-карта (вынута или там что-то не то)
    SamLicenseError(25),               //!< Ошибка проверки SAM лицензии

    NetworkModuleError(26),            //!< Ошибка в сетевом модуле
    UndefinedError(27);                //!< Неизвестная ошибка

  companion object {
    fun getByValue(value: Int) = ResultCode.values().firstOrNull { it.Code == value }
  }
}





///**
// * интерфейс обощающий все возможные варианты ответов от p7lib
// * TODO -  Юрий добавь описание
// *
// * @author - @FAHA
// */
//sealed class ResultCode(val code: Int)
//
///**
// * Успешный результат выполнения команды
// */
//object OK: ResultCode(0)
//
///**
// * Параметры библиотеки уже были успешно проинициализированы
// */
//object AlreadyInitialized : ResultCode(1)
//
///**
// *
// */
//object NonInitializedError : ResultCode(2)
//
///**
// *
// */
//object CardResetInitError : ResultCode(3)
//
///**
// *
// */
//object CardIoInitError : ResultCode(4)
//
///**
// *
// */
//object CardSelectError : ResultCode(5)
//
///**
// *
// */
//object CardAuthError : ResultCode(6)
//
///**
// *
// */
//object LoadIniError : ResultCode(7)
//
///**
// *
// */
//object LibFatalError : ResultCode(8)
//
///**
// *
// */
//object SequenceError : ResultCode(9)
//
///**
// *
// */
//object NotPetrol7Card : ResultCode(10)
//
///**
// *
// */
//object CardReadError : ResultCode(11)
//
///**
// *
// */
//object SamGetError : ResultCode(12)
//
///**
// *
// */
//object PinDataError : ResultCode(13)
//
///**
// *
// */
//object PinCheckError : ResultCode(14)
//
///**
// *
// */
//object DebitError : ResultCode(15)
//
///**
// *
// */
//object ArgAmountPriceSumError : ResultCode(16)
//
///**
// *
// */
//object ArgServiceError : ResultCode(17)
//
///**
// *
// */
//object ArgPinBlockError : ResultCode(18)
//
///**
// *
// */
//object ArgCardTypeJError : ResultCode(19)
//
///**
// *
// */
//object RefundError : ResultCode(20)
//
///**
// *
// */
//object SamResetInitError : ResultCode(21)
//
///**
// *
// */
//object SamIoInitError : ResultCode(22)
//
///**
// *
// */
//object SamSelectError : ResultCode(23)
//
///**
// *
// */
//object SamAuthInitError : ResultCode(24)
//
///**
// *
// */
//object SamLicenseError : ResultCode(25)
//
///**
// *
// */
//object NetworkModuleError : ResultCode(26)
//
///**
// *
// */
//object UndefinedError : ResultCode(27)




