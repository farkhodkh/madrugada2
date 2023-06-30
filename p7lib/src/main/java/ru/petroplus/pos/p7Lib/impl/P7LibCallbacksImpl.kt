package ru.petroplus.pos.p7Lib.impl

import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.dto.OK
import ru.petroplus.pos.p7LibApi.dto.PrintDataDto
import ru.petroplus.pos.p7LibApi.dto.ResultCode
import ru.petroplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petroplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petroplus.pos.p7LibApi.requests.ApduData
import ru.petroplus.pos.p7LibApi.responces.OperationResult

class P7LibCallbacksImpl : IP7LibCallbacks {
    override fun log(message: String) {
        val b = 0
    }

    override fun cardReset(): OperationResult {
        val b = 0
        return OperationResult(OK, byteArrayOf())
    }

    override fun sendDataToCard(data: ApduData): OperationResult {
        val b = 0
        return OperationResult(OK, byteArrayOf())
    }

    override fun samReset(): OperationResult {
        return OperationResult(OK, byteArrayOf())
    }

    override fun sendToSamCard(data: ApduData): OperationResult {
        return OperationResult(OK, byteArrayOf())
    }

    override fun connectToAS(timeUnit: Long): Boolean {
        return false
    }

    override fun sendToAS(data: ByteArray): OperationResult {
        return OperationResult(OK, byteArrayOf())
    }

    override fun findLastTransaction(
        cardNumber: Int,
        transactionEntity: TransactionInfoDto
    ): ResultCode {
        return OK
    }

    override fun updateTransaction(record: TransactionRecordDto) {
        val d = 0
    }

    override fun printSimpleDoc(data: PrintDataDto) {
        val f = 0
    }
}