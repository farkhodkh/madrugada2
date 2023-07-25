package ru.petroplus.pos.p7LibApi.dto

import ru.petroplus.pos.p7LibApi.dto.card.CardType

//        RecordData->CardNumber = RECORD(ONLINE_TRANZ_BASE)->CardNumber;
//        memcpy(&RecordData->TimeStamp, &RECORD(ONLINE_TRANZ_BASE)->TimeStamp, sizeof(RECORD(ONLINE_TRANZ_BASE)->TimeStamp));
//        RecordData->ServiceIdOrigEmit = RECORD(ONLINE_TRANZ_BASE)->ServiceIdOrigEmit;
//        RecordData->ServiceIdCurrEmit = RECORD(ONLINE_TRANZ_BASE)->ServiceIdCurrEmit;
//        RecordData->TotalVolume = RECORD(ONLINE_TRANZ_BASE)->TotalVolume;
//        RecordData->Price = RECORD(ONLINE_TRANZ_BASE)->Price;
//        RecordData->TotalSum = RECORD(ONLINE_TRANZ_BASE)->TotalSum;
//        RecordData->CardTrzCounter = RECORD(ONLINE_TRANZ_BASE)->CardTrzCounter;
//        RecordData->HasReturn = RECORD(ONLINE_TRANZ_BASE)->HasReturn;
//        memcpy(&RecordData->RollbackCode[0], &RECORD(ONLINE_TRANZ_BASE)->RollbackCode[0], sizeof(RECORD(ONLINE_TRANZ_BASE)->RollbackCode));
//        memcpy(&RecordData->DebitToken[0], &RECORD(ONLINE_TRANZ_BASE)->DebitToken[0], sizeof(RECORD(ONLINE_TRANZ_BASE)->DebitToken));
//        RecordData->TerminalNumber = RECORD(ONLINE_TRANZ_BASE)->TerminalNumber;
//        memcpy(&RecordData->Crc32[0], &RECORD(ONLINE_TRANZ_BASE)->Crc32, sizeof(RECORD(ONLINE_TRANZ_BASE)->Crc32));
//        RecordData->OperationType = RECORD(ONLINE_TRANZ_BASE)->OperationType;
//        RecordData->CardType = RECORD(ONLINE_TRANZ_BASE)->CardType;
//        RecordData->ClientSum = RECORD(ONLINE_TRANZ_BASE)->ClientSum;
//        RecordData->DeltaBonus = RECORD(ONLINE_TRANZ_BASE)->DeltaBonus;
//        memcpy(&RecordData->ReturnTimeStamp, &RECORD(ONLINE_TRANZ_BASE)->ReturnTimeStamp, sizeof(RECORD(ONLINE_TRANZ_BASE)->ReturnTimeStamp));
//ключ - DebitToken или просто Token
//+ номер смены
//+ тип операции (дебет/возврат)
/**
 * TODO - Юрий, добавиь описание класса и типов
 */

class TransactionRecordDto(
    val cardNumber: UInt = 0u,                   //!< Графический номер карты
    val shiftNumber: Int = 0,                    //!< Номер смены
    val timeStamp: Long = 0L,                    //!< Время проведения операции, привести к структуре STCLOCK
    val serviceIdOrigEmit: Byte = 0,             //!< Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
    val serviceIdCurrEmit: Byte = 0,             //!< Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
    val totalVolume: UInt = 0u,                  //!< Количество топлива/услуги ("что покупали")
    val price: UInt = 0u,                        //!< Цена за 1 ед. топлива/услуги ("что покупали")
    val totalSum: UInt = 0u,                     //!< Сумма (TotalVolume * Price)
    val cardTrzCounter: UShort = 0u,             //!< Номер операции (в терминах карты)
    val hasReturn: Boolean = false,              //!< Был ли возврат/отмена (0 - нет, 1 - да)
    val rollbackCode: ByteArray = byteArrayOf(), //!< Код для возврата (получен от карты во время дебета)
    val debitToken: ByteArray = byteArrayOf(),   //!< GUID транзакции дебета в онлайне
    val terminalNumber: UShort = 0u,             //!< Номер терминала/POS/поста
    val crc32: UInt = 0u,                        //!< CRC32 для данной записи
    val operationType: Byte = 0,                 //!< Тип транзакции (0 - дебет, 1 - кредит кошелька, 2 - онлайн-пополнение счета)
                                                 // не актуально, поле соответствует TrzBaseOperType

    val cardType: Byte = 0,                      //!< Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
    val clientSum: UInt = 0u,                    //!< Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
    val deltaBonus: UInt = 0u,                   //!< Начисленные бонусы при транзакции с лояльностью
    val returnTimeStamp: Long = 0L,              //!< Время проведения операции возврата/отмены по данному дебету/кредиту, привести к структуре STCLOCK
)