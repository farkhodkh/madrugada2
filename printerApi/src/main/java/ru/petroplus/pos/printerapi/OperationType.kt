package ru.petroplus.pos.printerapi

import ru.petroplus.pos.util.ResourceHelper

sealed class OperationType(val name: String) {
    object Debit : OperationType(ResourceHelper.getStringResource(R.string.debit_upper_case))
    object WalletCredit : OperationType(ResourceHelper.getStringResource(R.string.wallet_credit))
    object OnlineDeposit : OperationType(ResourceHelper.getStringResource(R.string.online_deposit))

    sealed class Return(description: String) : OperationType(description) {
        object ToCard : Return(ResourceHelper.getStringResource(R.string.return_to_card))
        object ToAccount : Return(ResourceHelper.getStringResource(R.string.return_to_account))
    }

    object Unknown : OperationType(ResourceHelper.getStringResource(R.string.unknown_operation))
}