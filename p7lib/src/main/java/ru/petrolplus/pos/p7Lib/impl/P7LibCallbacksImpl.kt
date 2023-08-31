package ru.petrolplus.pos.p7Lib.impl

import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.dto.OK
import ru.petrolplus.pos.p7LibApi.dto.PrintDataDto
import ru.petrolplus.pos.p7LibApi.dto.ResultCode
import ru.petrolplus.pos.p7LibApi.dto.TransactionInfoDto
import ru.petrolplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer
import ru.petrolplus.pos.p7LibApi.responces.OperationResult
import java.io.File

class P7LibCallbacksImpl : IP7LibCallbacks {
    override fun log(message: String) {
        val b = 0
    }

    override fun cardReset(answer: ApduAnswer): OperationResult {
        TODO("Not yet implemented")
    }

    override fun sendDataToCard(data: ApduData, answer: ApduAnswer): OperationResult {
        TODO("Not yet implemented")
    }

    override fun samReset(answer: ApduAnswer): OperationResult {
        TODO("Not yet implemented")
    }

    override fun sendToSamCard(data: ApduData, answer: ApduAnswer): OperationResult {
        TODO("Not yet implemented")
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

    override fun getPingData(): File {
        return File("")
    }
}