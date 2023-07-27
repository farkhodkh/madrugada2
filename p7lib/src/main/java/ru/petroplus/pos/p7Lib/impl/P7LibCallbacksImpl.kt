package ru.petroplus.pos.p7Lib.impl

import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.dto.*
import ru.petroplus.pos.p7LibApi.requests.ApduData
import ru.petroplus.pos.p7LibApi.responces.ApduAnswer
import ru.petroplus.pos.p7LibApi.responces.OperationResult

class P7LibCallbacksImpl : IP7LibCallbacks {
    override fun log(message: String) {
        val b = 0
    }

    override fun cardReset(answer: ApduAnswer): ResultCode {
        answer.sw1  = 0x00
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OK
    }

    override fun sendDataToCard(data: ApduData, answer: ApduAnswer): ResultCode {
        answer.sw1  = 0x90
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OK
    }

    override fun samReset(answer: ApduAnswer): ResultCode {
        answer.sw1  = 0x00
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()

        return OK
    }

    override fun sendDataToSam(data: ApduData, answer: ApduAnswer): ResultCode {
        answer.sw1  = 0x90
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OK
    }

    override fun connectToAS(timeUnit: Long): Boolean {
        return true
    }

    override fun doASDataExchange(data: ByteArray): OperationResult {
        val byteAnswer = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OperationResult(OK, byteAnswer)
    }

    override fun findLastTransactionDB(
        cardNumber: Long,
        record: TransactionRecordDto
    ): ResultCode {
        record.cardNumber = 3005876014L         //!< Графический номер карты
        record.shiftNumber = 0                  //!< Номер смены
        record.timeStamp = StClockDto()         //!< Время проведения операции, привести к структуре STCLOCK
        record.serviceIdOrigEmit = 0            //!< Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
        record.serviceIdCurrEmit = 0            //!< Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
        record.totalVolume = 0L                 //!< Количество топлива/услуги ("что покупали")
        record.price = 0L                       //!< Цена за 1 ед. топлива/услуги ("что покупали")
        record.totalSum = 0L                    //!< Сумма (TotalVolume * Price)
        record.cardTrzCounter = 0               //!< Номер операции (в терминах карты)
        record.hasReturn = false                //!< Был ли возврат/отмена (0 - нет, 1 - да)
        record.rollbackCode = byteArrayOf()     //!< Код для возврата (получен от карты во время дебета)
        record.debitToken = byteArrayOf()       //!< GUID транзакции дебета в онлайне
        record.terminalNumber = 0               //!< Номер терминала/POS/поста
        record.crc32 = byteArrayOf()            //!< CRC32 для данной записи
        record.operationType = 0                //!< Тип транзакции (0 - дебет, 1 - кредит кошелька, 2 - онлайн-пополнение счета)
                                                // не актуально, поле соответствует TrzBaseOperType

        record.cardType = 0                     //!< Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
        record.clientSum = 0L                   //!< Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
        record.deltaBonus = 0L                  //!< Начисленные бонусы при транзакции с лояльностью
        record.returnTimeStamp = StClockDto()   //!< Время проведения операции возврата/отмены по данному дебету/кредиту, привести к структуре STCLOCK

        return OK
    }

    override fun completeTransactionDB(record: TransactionRecordDto): ResultCode {
        val d = 0
        return OK
    }

    override fun printSimpleDoc(data: SimpleDocDto): ResultCode {
        val f = 0
        return OK
    }

    override fun transferOOBToAS(OOBData: ByteArray): ResultCode {

        return OK
    }
}