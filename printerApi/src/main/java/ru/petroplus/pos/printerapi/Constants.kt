package ru.petroplus.pos.printerapi

object IntroductoryConstruction {
    const val SERVICE_SUM = "Итог"
    const val SERVICE_PRICE = "Цена за"
    const val SERVICE_AMOUNT = "Кол-во"
    const val SERVICE = "Услуга"
    const val RECEIPT_NUMBER = "Чек No."
    const val RECEIPT_NUMBER_DENIAL = "ЧЕК ОТКАЗА"
    const val POS_NUMBER_EN = "POS No."
    const val POS_NUMBER_RU = "Терминал No."
    const val CART = "Карта"
    const val INN = "ИНН"
    const val TRANSACTION_CONFIRMED_BY_PIN_PART_I = "Операция подтверждена"
    const val TRANSACTION_CONFIRMED_BY_PIN_PART_II = "вводом ПИН кода"
    const val OPERATOR_NUMBER = "Оператор No."
    const val FOOTER_TEXT = "Добро пожаловать"
    const val PRICE_UNIT = "P"
    const val DENIAL = "ОТКАЗ"
    const val DENIAL_CODE = "Код отказа"
}
sealed class ResponseCode(val code: Int, val description: String) {
    object Success: ResponseCode(SUCCESS, "ОДОБРЕНО")

    object Canceled: ResponseCode(CANCELED, "Операция отменена на терминале")
    object Timeout: ResponseCode(TIMEOUT, "Тайм-аут операции")
    object ServiceProhibited: ResponseCode(SERVICE_PROHIBITED, "Товар/услуга запрещена")

    companion object {
        const val SUCCESS = 0

        // TODO: изменить коды ошибок в соответствии с документацией
        const val CANCELED = 1
        const val TIMEOUT = 2
        const val SERVICE_PROHIBITED = 3
    }
}

enum class PrinterState {
    WAIT_DOCUMENT,
    PRINT_FAILED,
    PRINTING
}