package ru.petrolplus.pos.p7Lib.impl

import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.OnP7LibResultListener
import ru.petrolplus.pos.p7LibApi.dto.TransactionRecordDto
import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer
import ru.petrolplus.pos.p7LibApi.responces.OK
import ru.petrolplus.pos.p7LibApi.dto.*
import ru.petrolplus.pos.p7LibApi.responces.CardReadError
import ru.petrolplus.pos.p7LibApi.responces.OperationResult
import ru.petrolplus.pos.p7LibApi.responces.ResultCode
import ru.petrolplus.pos.util.ext.isCorrectEvotorAtr

class P7LibCallbacksImpl : IP7LibCallbacks {
    override lateinit var listener: OnP7LibResultListener

    override fun log(message: String) {
        //TODO: код подлежит переработке
        val b = 0
    }

    override fun cardReset(answer: ApduAnswer): ResultCode {
        val atr = listener.onCardReset(answer)

        return if (atr?.isCorrectEvotorAtr() == true) {
            answer.sw1  = 0x90
            answer.sw2  = 0x00
            answer.data = atr.toByteArray()
            OK
        } else {
            CardReadError
        }
    }

    override fun sendDataToCard(data: ApduData, answer: ApduAnswer): ResultCode {
        val data = listener.onSendDataToCard(data, answer)

        return if (data == null) {
            CardReadError
        } else {
            answer.sw1  = 0x90
            answer.sw2  = 0x00
            answer.data = data
            OK
        }
    }

    override fun samReset(answer: ApduAnswer): ResultCode {
        //TODO: код подлежит переработке
        answer.sw1  = 0x00
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()

        return OK
    }

    override fun sendDataToSam(data: ApduData, answer: ApduAnswer): ResultCode {
        //TODO: код подлежит переработке
        answer.sw1  = 0x90
        answer.sw2  = 0x00
        answer.data = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OK
    }

    override fun connectToAS(timeUnit: Long): Boolean {
        //TODO: код подлежит переработке
        return true
    }

    override fun doASDataExchange(data: ByteArray): OperationResult {
        //TODO: код подлежит переработке
        val byteAnswer = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        return OperationResult(OK, byteAnswer)
    }

    override fun findLastTransactionDB(
        cardNumber: Long,
        record: TransactionRecordDto
    ): ResultCode {
        //TODO: код подлежит переработке
        record.cardNumber = 3005876014L         //!< Графический номер карты
        record.shiftNumber = 0                  //!< Номер смены
        record.timeStamp = ClockDto()           //!< Время проведения операции, привести к структуре STCLOCK
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
        record.returnTimeStamp = ClockDto()     //!< Время проведения операции возврата/отмены по данному дебету/кредиту, привести к структуре STCLOCK

        return OK
    }

    override fun completeTransactionDB(record: TransactionRecordDto): ResultCode {
        //TODO: код подлежит переработке
        val d = 0
        return OK
    }

    override fun printSimpleDoc(data: PrintableDataDto): ResultCode {
        //TODO: код подлежит переработке
        val f = 0
        return OK
    }

    override fun transferOOBToAS(oobData: ByteArray): ResultCode {
        //TODO: код подлежит переработке

        return OK
    }
}