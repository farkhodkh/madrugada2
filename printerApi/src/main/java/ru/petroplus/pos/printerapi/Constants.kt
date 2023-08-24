package ru.petroplus.pos.printerapi

import ru.petroplus.pos.util.ResourceHelper


object ReceiptFormatting {
    const val RECEIPT_MASK_SIZE = 10
    const val TERMINAL_NUMBER_MASK_SIZE = 5
}

object IntroductoryConstruction {
    val SERVICE_SUM = ResourceHelper.getStringResource(R.string.service_sum)
    val SERVICE_PRICE = ResourceHelper.getStringResource(R.string.service_price)
    val SERVICE_AMOUNT = ResourceHelper.getStringResource(R.string.service_amount)
    val SERVICE = ResourceHelper.getStringResource(R.string.service)
    val RECEIPT_NUMBER = ResourceHelper.getStringResource(R.string.service_number)
    val RECEIPT_NUMBER_DENIAL = ResourceHelper.getStringResource(R.string.service_number_denial)
    val POS_NUMBER_EN = ResourceHelper.getStringResource(R.string.pos_number_en)
    val POS_NUMBER_RU = ResourceHelper.getStringResource(R.string.pos_number_ru)
    val CARD = ResourceHelper.getStringResource(R.string.card)
    val INN = ResourceHelper.getStringResource(R.string.inn)
    val OPERATION_CONFIRMED =
        ResourceHelper.getStringResource(R.string.operation_confirmed)
    val DEBIT_CONFIRMED_BY_PIN =
        ResourceHelper.getStringResource(R.string.debit_confirmed_by_pin)
    val RETURN_CONFIRMED_BY_TERMINAL =
        ResourceHelper.getStringResource(R.string.return_confirmed_by_terminal)
    val OPERATOR_NUMBER = ResourceHelper.getStringResource(R.string.operator_number)
    val FOOTER_TEXT = ResourceHelper.getStringResource(R.string.footer_text)
    val PRICE_UNIT = ResourceHelper.getStringResource(R.string.price_unit)
    val DENIAL = ResourceHelper.getStringResource(R.string.denial)
    val DENIAL_CODE = ResourceHelper.getStringResource(R.string.denial_code)
}

sealed class ResponseCode(val code: Int, val description: String) {
    object Success : ResponseCode(SUCCESS, "ОДОБРЕНО")

    sealed class Error(code: Int, description: String) : ResponseCode(code, description) {
        object Universal : Error(UNIVERSAL_ERROR, "Универсальная ошибка")
        object System : Error(SYSTEM_ERROR, "Системная ошибка")
        object LibInit : Error(LIB_INT_ERROR, "Системная ошибка инициализации")
        object Ini : Error(INI_FAIL, "Ошибка с ini-файлом")
        object Call : Error(CALL_ERROR, "Некорректный вызов библиотеки извне")
        object WrongCard : Error(WRONG_CARD, "Не Petrol 7")
        object BadCard : Error(BAD_CARD, "Ошибка чтения/работы с картой")
        object BadSam : Error(BAD_SAM, "Проблемы с SAM")
        object PinFail : Error(PIN_FAIL, "Ошибка проверки PIN")
        object WrongPin : Error(WRONG_PIN, "Hеверный PIN")
        object WrongArgs : Error(WRONG_ARGS, "Неверный заказ")
        object WrongService : Error(WRONG_SRV, "Неверная услуга")
        object DebitFail : Error(DEBIT_FAIL, "Ошибка дебета")
        object RefundFail : Error(REFUND_FAIL, "Ошибка возврата")
        object FatalError : Error(FATAL_ERROR, "Ошибка обслуживания")
        object NetError : Error(NET_ERROR, "Ошибка запроса АС")
        object ASTimout : Error(AS_TIMEOUT, "Таймаут авторизации")
        object ASLimits : Error(AS_LIMITS, "Нет онлайн лимитов")
        object ASExtAuth : Error(AS_EXT_AUTH, "Требуется доп.авторизация")
        object ASBlocked : Error(AS_BLOCKED, "Карта заблокирована")
        object ASNoFunds : Error(AS_NO_FUNDS, "Недостаточно средств")
        object ASNoLimits : Error(AS_NO_LIMITS, "Превышение лимита")
        object ASPin : Error(AS_PIN_ERROR, "Неверный PIN")
        object ASNoService : Error(AS_NO_SERVICE, "Сервис недоступен")
        object ASNoTime : Error(AS_NO_TIME, "В текущее время не обслуживается")
        object ASUnknown : Error(AS_UNKNOWN, "Карта неизвестна")
        object ASError : Error(AS_ERROR, "Ошибка на АС")
        object ASCommError : Error(AS_COMM_ERROR, "Ошибка на АС")
        object ASBlExp : Error(AS_BL_EXP, "ЧС на АС устарел")
        object ASCardExp : Error(AS_CARD_EXP, "Срок действия карты истек")
        object ASNoEm : Error(AS_NO_EM, "Прием данного эмитента не разрешен")
        object ASNoTerm : Error(AS_NO_TERM, "Неверный номер терминала")
        object ASBl : Error(BL_ERROR, "Ошибка проверки ЧС")
        object SrvError : Error(SRV_ERROR, "Нет услуги")
        object LimitError : Error(LIM_ERROR, "Лимит исчерпан")
        object AmountError : Error(AMOUNT_ERROR, "Недостаточно средств")
        object GenAcError : Error(GEN_AC_ERROR, "Ошибка на карте")
    }

    companion object {
        const val SUCCESS = 0

        const val UNIVERSAL_ERROR = -1
        const val SYSTEM_ERROR = 1
        const val LIB_INT_ERROR = 2
        const val INI_FAIL = 3
        const val CALL_ERROR = 4
        const val WRONG_CARD = 5
        const val BAD_CARD = 6
        const val BAD_SAM = 7
        const val PIN_FAIL = 8
        const val WRONG_PIN = 9
        const val WRONG_ARGS = 10
        const val WRONG_SRV = 11
        const val DEBIT_FAIL = 12
        const val REFUND_FAIL = 13
        const val FATAL_ERROR = 14
        const val NET_ERROR = 15
        const val AS_TIMEOUT = 16
        const val AS_LIMITS = 17
        const val AS_EXT_AUTH = 18
        const val AS_BLOCKED = 19
        const val AS_NO_FUNDS = 20
        const val AS_NO_LIMITS = 21
        const val AS_PIN_ERROR = 22
        const val AS_NO_SERVICE = 23
        const val AS_NO_TIME = 24
        const val AS_UNKNOWN = 25
        const val AS_ERROR = 26
        const val AS_COMM_ERROR = 27
        const val AS_BL_EXP = 28
        const val AS_CARD_EXP = 29
        const val AS_NO_EM = 30
        const val AS_NO_TERM = 31
        const val BL_ERROR = 32
        const val SRV_ERROR = 33
        const val LIM_ERROR = 34
        const val AMOUNT_ERROR = 35
        const val GEN_AC_ERROR = 36
    }
}