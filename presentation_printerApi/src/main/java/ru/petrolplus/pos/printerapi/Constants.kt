package ru.petrolplus.pos.printerapi

import ru.petrolplus.pos.util.ResourceHelper


object ReceiptFormatting {
    const val RECEIPT_MASK_SIZE = 10
    const val TERMINAL_NUMBER_MASK_SIZE = 5
}

object IntroductoryConstruction {
    val SERVICE_SUM by lazy { ResourceHelper.getStringResource(R.string.service_sum) }
    val SERVICE_PRICE by lazy { ResourceHelper.getStringResource(R.string.service_price) }
    val SERVICE_AMOUNT by lazy { ResourceHelper.getStringResource(R.string.service_amount) }
    val SERVICE by lazy { ResourceHelper.getStringResource(R.string.service) }
    val RECEIPT_NUMBER by lazy { ResourceHelper.getStringResource(R.string.service_number) }
    val RECEIPT_NUMBER_DENIAL by lazy { ResourceHelper.getStringResource(R.string.service_number_denial) }
    val POS_NUMBER_EN by lazy { ResourceHelper.getStringResource(R.string.pos_number_en) }
    val POS_NUMBER_RU by lazy { ResourceHelper.getStringResource(R.string.pos_number_ru) }
    val CARD by lazy { ResourceHelper.getStringResource(R.string.card) }
    val INN by lazy { ResourceHelper.getStringResource(R.string.inn) }
    val OPERATION_CONFIRMED_BY_PIN by lazy { ResourceHelper.getStringResource(R.string.operation_confirmed_by_pin) }
    val DEBIT_CONFIRMED_BY_PIN by lazy { ResourceHelper.getStringResource(R.string.debit_confirmed_by_pin) }
    val OPERATION_CONFIRMED_BY_TERMINAL by lazy { ResourceHelper.getStringResource(R.string.operation_confirmed_by_terminal) }
    val RETURN_CONFIRMED_BY_TERMINAL by lazy { ResourceHelper.getStringResource(R.string.return_confirmed_by_terminal) }
    val OPERATOR_NUMBER by lazy { ResourceHelper.getStringResource(R.string.operator_number) }
    val FOOTER_TEXT by lazy { ResourceHelper.getStringResource(R.string.footer_text) }
    val PRICE_UNIT by lazy { ResourceHelper.getStringResource(R.string.price_unit) }
    val DENIAL by lazy { ResourceHelper.getStringResource(R.string.denial) }
    val DENIAL_CODE by lazy { ResourceHelper.getStringResource(R.string.denial_code) }
}

sealed class ResponseCode(val code: Int, val description: String) {
    object Success : ResponseCode(SUCCESS, ResourceHelper.getStringResource(R.string.success_response) )

    sealed class Error(code: Int, description: String) : ResponseCode(code, description) {
        object Universal : Error(UNIVERSAL_ERROR, ResourceHelper.getStringResource(R.string.universal_error))
        object System : Error(SYSTEM_ERROR, ResourceHelper.getStringResource(R.string.system_error))
        object LibInit : Error(LIB_INT_ERROR, ResourceHelper.getStringResource(R.string.lib_init))
        object Ini : Error(INI_FAIL, ResourceHelper.getStringResource(R.string.ini))
        object Call : Error(CALL_ERROR, ResourceHelper.getStringResource(R.string.call_error))
        object WrongCard : Error(WRONG_CARD, ResourceHelper.getStringResource(R.string.worng_card))
        object BadCard : Error(BAD_CARD, ResourceHelper.getStringResource(R.string.bad_card))
        object BadSam : Error(BAD_SAM, ResourceHelper.getStringResource(R.string.bad_sam))
        object PinFail : Error(PIN_FAIL, ResourceHelper.getStringResource(R.string.pin_fail))
        object WrongPin : Error(WRONG_PIN, ResourceHelper.getStringResource(R.string.wrong_pin))
        object WrongArgs : Error(WRONG_ARGS, ResourceHelper.getStringResource(R.string.wrong_args))
        object WrongService : Error(WRONG_SRV, ResourceHelper.getStringResource(R.string.wrong_service))
        object DebitFail : Error(DEBIT_FAIL, ResourceHelper.getStringResource(R.string.debit_fail))
        object RefundFail : Error(REFUND_FAIL, ResourceHelper.getStringResource(R.string.refund_fail))
        object FatalError : Error(FATAL_ERROR, ResourceHelper.getStringResource(R.string.fatal_error))
        object NetError : Error(NET_ERROR, ResourceHelper.getStringResource(R.string.net_error))
        object ASTimout : Error(AS_TIMEOUT, ResourceHelper.getStringResource(R.string.as_timout))
        object ASLimits : Error(AS_LIMITS, ResourceHelper.getStringResource(R.string.as_limits))
        object ASExtAuth : Error(AS_EXT_AUTH, ResourceHelper.getStringResource(R.string.as_ext_auth))
        object ASBlocked : Error(AS_BLOCKED, ResourceHelper.getStringResource(R.string.as_blocked))
        object ASNoFunds : Error(AS_NO_FUNDS, ResourceHelper.getStringResource(R.string.as_no_founds))
        object ASNoLimits : Error(AS_NO_LIMITS, ResourceHelper.getStringResource(R.string.as_no_limits))
        object ASPin : Error(AS_PIN_ERROR, ResourceHelper.getStringResource(R.string.as_pin_error))
        object ASNoService : Error(AS_NO_SERVICE, ResourceHelper.getStringResource(R.string.as_no_service))
        object ASNoTime : Error(AS_NO_TIME,  ResourceHelper.getStringResource(R.string.as_no_time))
        object ASUnknown : Error(AS_UNKNOWN, ResourceHelper.getStringResource(R.string.as_unknown))
        object ASError : Error(AS_ERROR, ResourceHelper.getStringResource(R.string.as_error))
        object ASCommError : Error(AS_COMM_ERROR, ResourceHelper.getStringResource(R.string.as_comm_error))
        object ASBlExp : Error(AS_BL_EXP, ResourceHelper.getStringResource(R.string.as_bl_exp))
        object ASCardExp : Error(AS_CARD_EXP, ResourceHelper.getStringResource(R.string.as_card_exp))
        object ASNoEm : Error(AS_NO_EM, ResourceHelper.getStringResource(R.string.as_no_em))
        object ASNoTerm : Error(AS_NO_TERM, ResourceHelper.getStringResource(R.string.as_no_term))
        object ASBl : Error(BL_ERROR, ResourceHelper.getStringResource(R.string.as_bl))
        object SrvError : Error(SRV_ERROR, ResourceHelper.getStringResource(R.string.srv_error))
        object LimitError : Error(LIM_ERROR, ResourceHelper.getStringResource(R.string.limit_error))
        object AmountError : Error(AMOUNT_ERROR, ResourceHelper.getStringResource(R.string.amount_error))
        object GenAcError : Error(GEN_AC_ERROR, ResourceHelper.getStringResource(R.string.gen_ac_error))
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