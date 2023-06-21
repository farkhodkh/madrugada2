package ru.petroplus.pos.p7Lib.impl

import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.p7LibApi.`typealias`.TransactionUUID
import ru.petroplus.pos.p7LibApi.dto.*
import ru.petroplus.pos.p7LibApi.dto.card.CardInfo

class P7LibRepositoryImpl : IP7LibRepository {
    external override fun init(
        initData: InitDataDto,
        lastOpGUID: TransactionUUID,
        callbacks: IP7LibCallbacks,
        tempDir: String,
        dataDir: String
    ): ResultCode

    external override fun deInit(): ResultCode

    external override fun detect(cardKey: CardKeyDto, cardData: CardInfo): ResultCode

    external override fun debit(
        params: DebitParamsDto,
        info: TransactionInfoDto,
        uuid: TransactionUUID
    ): ResultCode

    external override fun refund(
        params: RefundParamsDto,
        info: TransactionInfoDto,
        uuid: TransactionUUID
    ): ResultCode

    external override fun getErrorInfo(errorInfo: ErrorInfoDto): ResultCode

    external override fun getLibInfo(libInfo: LibInfoDto): ResultCode
}