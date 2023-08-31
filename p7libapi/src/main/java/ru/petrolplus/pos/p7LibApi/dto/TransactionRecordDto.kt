package ru.petrolplus.pos.p7LibApi.dto

import ru.petrolplus.pos.p7LibApi.dto.card.CardType

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
    val cardNumber: Int,
    val shiftNumber: Long,
    val timeStamp: Long,
    val serviceOriginalEmit: Byte,
    val serviceCurrentEmit: Byte,
    val totalVolume: Double,
    val price: Double,
    val totalSum: Double,
    val cardTrzCounter: Int,
    val hasReturn: Boolean,
    val rollbackCode: Int,
    val debitToken: String,
    val terminalNumber: Int,
    val crc32: Int,
    val operationType: Int,
    val cardType: CardType,
    val clientSum: Double,
    val deltaBonus: Double,
    val returnTimeStamp: Long,
)