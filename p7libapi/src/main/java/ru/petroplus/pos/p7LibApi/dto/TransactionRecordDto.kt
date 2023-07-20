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
    val cardNumber: UInt,            //!< Графический номер карты
    val shiftNumber: Int,            //!< Номер смены
    val timeStamp: Long,             //!< Время проведения операции, привести к структуре STCLOCK
    val serviceIdOrigEmit: Byte,     //!< Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
    val serviceIdCurrEmit: Byte,     //!< Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
    val totalVolume: UInt,           //!< Количество топлива/услуги ("что покупали")
    val price: UInt,                 //!< Цена за 1 ед. топлива/услуги ("что покупали")
    val totalSum: UInt,              //!< Сумма (TotalVolume * Price)
    val cardTrzCounter: UShort,      //!< Номер операции (в терминах карты)
    val hasReturn: Boolean,          //!< Был ли возврат/отмена (0 - нет, 1 - да)
    val rollbackCode: ByteArray,     //!< Код для возврата (получен от карты во время дебета)
    val debitToken: ByteArray,       //!< GUID транзакции дебета в онлайне
    val terminalNumber: UShort,      //!< Номер терминала/POS/поста
    val crc32: UInt,                 //!< CRC32 для данной записи
    val operationType: Byte,         //!< Тип транзакции (0 - дебет, 1 - кредит кошелька, 2 - онлайн-пополнение счета)
                                     // не актуально, поле соответствует TrzBaseOperType

    val cardType: Byte,              //!< Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
    val clientSum: UInt,             //!< Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
    val deltaBonus: UInt,            //!< Начисленные бонусы при транзакции с лояльностью
    val returnTimeStamp: Long,       //!< Время проведения операции возврата/отмены по данному дебету/кредиту, привести к структуре STCLOCK
)