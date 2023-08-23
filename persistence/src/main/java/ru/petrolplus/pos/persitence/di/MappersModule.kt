package ru.petrolplus.pos.persitence.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.persitence.dto.ReceiptDTO
import ru.petrolplus.pos.persitence.dto.ReceiptParamsDTO
import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO
import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.ServiceDTO
import ru.petrolplus.pos.persitence.dto.ShiftParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.BaseSettingsMapper
import ru.petrolplus.pos.persitence.mappers.CommonSettingsMapper
import ru.petrolplus.pos.persitence.mappers.ReceiptMapper
import ru.petrolplus.pos.persitence.mappers.GUIDparamsMapper
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.persitence.mappers.ProjectionMapper
import ru.petrolplus.pos.persitence.mappers.ReceiptParamsMapper
import ru.petrolplus.pos.persitence.mappers.ServicesMapper
import ru.petrolplus.pos.persitence.mappers.ShiftParamsMapper
import ru.petrolplus.pos.persitence.mappers.TransactionsMapper
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.GUIDParamsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB
import ru.petrolplus.pos.room.projections.ReceiptProjection
import ru.petroplus.pos.core.MainScreenScope

@Module
class MappersModule {

    @[Provides MainScreenScope]
    fun providesBaseSettingsMapper(): Mapper<BaseSettingsDTO, BaseSettingsDB> = BaseSettingsMapper()

    @[Provides MainScreenScope]
    fun providesCommonSettingsMapper(): Mapper<CommonSettingsDTO, CommonSettingsDB> = CommonSettingsMapper()

    @[Provides MainScreenScope]
    fun providesGUIDparamsMapper(): Mapper<GUIDParamsDTO, GUIDParamsDB> = GUIDparamsMapper()

    @[Provides MainScreenScope]
    fun providesShiftParamsMapper(): Mapper<ShiftParamsDTO, ShiftParamsDB> = ShiftParamsMapper()

    @[Provides MainScreenScope]
    fun providesServicesMapper(): Mapper<ServiceDTO, ServiceDB> = ServicesMapper()

    @[Provides MainScreenScope]
    fun providesTransactionsMapper(): Mapper<TransactionDTO, TransactionDB> = TransactionsMapper()

    @[Provides MainScreenScope]
    fun providesReceiptParamsMapper(): Mapper<ReceiptParamsDTO, ReceiptParamsDB> = ReceiptParamsMapper()

    @[Provides MainScreenScope]
    fun providesReceiptMapper(): ProjectionMapper<ReceiptProjection, ReceiptDTO> = ReceiptMapper()


}