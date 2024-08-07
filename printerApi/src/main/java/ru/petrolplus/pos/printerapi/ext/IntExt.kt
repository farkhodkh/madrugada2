package ru.petrolplus.pos.printerapi.ext

import ru.petrolplus.pos.persitence.enum.CardType
import ru.petrolplus.pos.persitence.enum.OperationType
import ru.petrolplus.pos.printerapi.Formatting
import ru.petrolplus.pos.printerapi.ResponseCode
import ru.petrolplus.pos.util.ext.leadingZeros

fun Int.formattingTerminalId() = this.leadingZeros(Formatting.TERMINAL_NUMBER_MASK_SIZE)

fun Int.toCardType(): CardType = when (this) {
    CardType.PETROL_5.id -> CardType.PETROL_5
    CardType.PETROL_7.id -> CardType.PETROL_7
    else -> CardType.UNKNOWN
}

fun Int.toOperationType(): OperationType = when (this) {
    OperationType.DEBIT.id -> OperationType.DEBIT
    OperationType.WALLET_CREDIT.id -> OperationType.WALLET_CREDIT
    OperationType.ONLINE_REFILL.id -> OperationType.ONLINE_REFILL
    OperationType.CARD_REFUND.id -> OperationType.CARD_REFUND
    OperationType.ACCOUNT_REFUND.id -> OperationType.ACCOUNT_REFUND
    else -> OperationType.UNKNOWN
}

fun Int.toResponseCode(): ResponseCode = when (this) {
    ResponseCode.SUCCESS -> ResponseCode.Success
    ResponseCode.SYSTEM_ERROR -> ResponseCode.Error.System
    ResponseCode.LIB_INT_ERROR -> ResponseCode.Error.LibInit
    ResponseCode.INI_FAIL -> ResponseCode.Error.Ini
    ResponseCode.CALL_ERROR -> ResponseCode.Error.Call
    ResponseCode.WRONG_CARD -> ResponseCode.Error.WrongCard
    ResponseCode.BAD_CARD -> ResponseCode.Error.BadCard
    ResponseCode.BAD_SAM -> ResponseCode.Error.BadSam
    ResponseCode.PIN_FAIL -> ResponseCode.Error.PinFail
    ResponseCode.WRONG_PIN -> ResponseCode.Error.WrongPin
    ResponseCode.WRONG_ARGS -> ResponseCode.Error.WrongArgs
    ResponseCode.WRONG_SRV -> ResponseCode.Error.WrongService
    ResponseCode.DEBIT_FAIL -> ResponseCode.Error.DebitFail
    ResponseCode.REFUND_FAIL -> ResponseCode.Error.RefundFail
    ResponseCode.FATAL_ERROR -> ResponseCode.Error.FatalError
    ResponseCode.NET_ERROR -> ResponseCode.Error.NetError
    ResponseCode.AS_TIMEOUT -> ResponseCode.Error.ASTimout
    ResponseCode.AS_LIMITS -> ResponseCode.Error.ASLimits
    ResponseCode.AS_EXT_AUTH -> ResponseCode.Error.ASExtAuth
    ResponseCode.AS_BLOCKED -> ResponseCode.Error.ASBlocked
    ResponseCode.AS_NO_FUNDS -> ResponseCode.Error.ASNoFunds
    ResponseCode.AS_NO_LIMITS -> ResponseCode.Error.ASNoLimits
    ResponseCode.AS_PIN_ERROR -> ResponseCode.Error.ASPin
    ResponseCode.AS_NO_SERVICE -> ResponseCode.Error.ASNoService
    ResponseCode.AS_NO_TIME -> ResponseCode.Error.ASNoTime
    ResponseCode.AS_UNKNOWN -> ResponseCode.Error.ASUnknown
    ResponseCode.AS_ERROR -> ResponseCode.Error.ASError
    ResponseCode.AS_COMM_ERROR -> ResponseCode.Error.ASCommError
    ResponseCode.AS_BL_EXP -> ResponseCode.Error.ASBlExp
    ResponseCode.AS_CARD_EXP -> ResponseCode.Error.ASCardExp
    ResponseCode.AS_NO_EM -> ResponseCode.Error.ASNoEm
    ResponseCode.AS_NO_TERM -> ResponseCode.Error.ASNoTerm
    ResponseCode.BL_ERROR -> ResponseCode.Error.ASBl
    ResponseCode.SRV_ERROR -> ResponseCode.Error.SrvError
    ResponseCode.LIM_ERROR -> ResponseCode.Error.LimitError
    ResponseCode.AMOUNT_ERROR -> ResponseCode.Error.AmountError
    ResponseCode.GEN_AC_ERROR -> ResponseCode.Error.GenAcError
    else -> ResponseCode.Error.Universal
}
