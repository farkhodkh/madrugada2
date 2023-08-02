package ru.petroplus.pos.p7LibApi.dto

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

class TransactionRecordDto(
    var cardNumber: Long = 0L,                      //!< Графический номер карты
    var shiftNumber: Long = 0,                      //!< Номер смены
    var timeStamp: ClockDto = ClockDto(),           //!< Время проведения операции, привести к структуре STCLOCK
    var serviceIdOrigEmit: Byte = 0,                //!< Вид топлива/услуги "за что платили" (в терминах ЭМИТЕНТА карты)
    var serviceIdCurrEmit: Byte = 0,                //!< Вид топлива/услуги "чем платили" (в терминах ЭМИТЕНТА карты)
    var totalVolume: Long = 0L,                     //!< Количество топлива/услуги ("что покупали")
    var price: Long = 0L,                           //!< Цена за 1 ед. топлива/услуги ("что покупали")
    var totalSum: Long = 0L,                        //!< Сумма (TotalVolume * Price)
    var cardTrzCounter: Int = 0,                    //!< Номер операции (в терминах карты)
    var hasReturn: Boolean = false,                 //!< Был ли возврат/отмена (0 - нет, 1 - да)
    var rollbackCode: ByteArray = byteArrayOf(),    //!< Код для возврата (получен от карты во время дебета)
    var debitToken: ByteArray = byteArrayOf(),      //!< GUID транзакции дебета в онлайне
    var terminalNumber: Int = 0,                    //!< Номер терминала/POS/поста
    var crc32: ByteArray = byteArrayOf(),           //!< CRC32 для данной записи
    var operationType: Byte = 0,                    //!< Тип транзакции (0 - дебет, 1 - кредит кошелька, 2 - онлайн-пополнение счета)
                                                    // не актуально, поле соответствует TrzBaseOperType

    var cardType: Byte = 0,                         //!< Тип карты (1 - обычная петроловская, 2 - java, 0 - тип карты неизвестен)
    var clientSum: Long = 0L,                       //!< Сумма с учётом скидки (для поддержки дебета с лояльностью по обычным петрольным картам)
    var deltaBonus: Long = 0L,                      //!< Начисленные бонусы при транзакции с лояльностью
    var returnTimeStamp: ClockDto = ClockDto()      //!< Время проведения операции возврата/отмены по данному дебету/кредиту, привести к структуре STCLOCK
)