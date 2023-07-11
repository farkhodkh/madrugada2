package ru.petroplus.pos.p7Lib.impl

import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.dto.OK
import ru.petroplus.pos.p7LibApi.dto.PrintDataDto
import ru.petroplus.pos.p7LibApi.dto.ResultCode
import ru.petroplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petroplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petroplus.pos.p7LibApi.requests.ApduData
import ru.petroplus.pos.p7LibApi.responces.ApduAnswer
import ru.petroplus.pos.p7LibApi.responces.OperationResult

class P7LibCallbacksImpl : IP7LibCallbacks {
    override fun log(message: String) {
        val b = 0
    }

    override fun cardReset(answer: ApduAnswer): ResultCode {
        val b = 0
        return OK
    }

    override fun sendDataToCard(data: ApduData, answer: ApduAnswer): ResultCode {
        val b = 0
        return OK
    }

    override fun samReset(answer: ApduAnswer): ResultCode {
        return OK
    }

    override fun sendDataToSam(data: ApduData, answer: ApduAnswer): ResultCode {
        return OK
    }

    override fun connectToAS(timeUnit: Long): Boolean {
        return false
    }

    override fun doASDataExchange(data: ByteArray): OperationResult {
        return OperationResult(OK, byteArrayOf())
    }

    override fun findLastTransactionDB(
        cardNumber: Int,
        record: TransactionRecordDto
    ): ResultCode {
        return OK
    }

    override fun completeTransactionDB(record: TransactionRecordDto): ResultCode {
        val d = 0
        return OK
    }

    override fun printSimpleDoc(data: PrintDataDto): ResultCode {
        val f = 0
        return OK
    }
}