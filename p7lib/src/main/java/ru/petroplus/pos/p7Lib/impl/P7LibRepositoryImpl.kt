package ru.petroplus.pos.p7Lib.impl

import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.p7LibApi.dto.*
import ru.petroplus.pos.p7LibApi.dto.card.CardInfo

class P7LibRepositoryImpl : IP7LibRepository {
    init {
        System.loadLibrary("p7lib")
    }

    external override fun init(
        initData: InitDataDto,
        lastOpGUID: TransactionUUIDDto,
        callbacks: IP7LibCallbacks,
        tempDir: String,
        dataDir: String
    ): ResultCode

    external override fun deInit(): ResultCode

    external override fun detect(cardKey: CardKeyDto, cardData: CardInfo): ResultCode

    external override fun debit(
        params: DebitParamsDto,
        info: TransactionInfoDto,
        transactionUuid: TransactionUUIDDto
    ): ResultCode

    external override fun refund(
        params: RefundParamsDto,
        info: TransactionInfoDto,
        transactionUuid: TransactionUUIDDto
    ): ResultCode

    external override fun getErrorInfo(errorInfo: ErrorInfoDto): ResultCode

    external override fun getLibInfo(libInfo: LibInfoDto): ResultCode
}