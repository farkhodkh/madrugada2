package ru.petrolplus.pos.persitence.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.persitence.entities.BaseSettingsDTO
import ru.petrolplus.pos.persitence.entities.CommonSettingsDTO
import ru.petrolplus.pos.persitence.entities.GUIDparamsDTO
import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petrolplus.pos.persitence.entities.ShiftParamsDTO
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.BaseSettingsMapper
import ru.petrolplus.pos.persitence.mappers.CommonSettingsMapper
import ru.petrolplus.pos.persitence.mappers.GUIDparamsMapper
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.persitence.mappers.ServicesMapper
import ru.petrolplus.pos.persitence.mappers.ShiftParamsMapper
import ru.petrolplus.pos.persitence.mappers.TransactionsMapper
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.GUIDparamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB

@Module
class MappersModule {

    @Provides
    fun providesBaseSettingsMapper(): Mapper<BaseSettingsDTO, BaseSettingsDB> = BaseSettingsMapper()

    @Provides
    fun providesCommonSettingsMapper(): Mapper<CommonSettingsDTO, CommonSettingsDB> = CommonSettingsMapper()

    @Provides
    fun providesGUIDparamsMapper(): Mapper<GUIDparamsDTO, GUIDparamsDB> = GUIDparamsMapper()

    @Provides
    fun providesShiftParamsMapper(): Mapper<ShiftParamsDTO, ShiftParamsDB> = ShiftParamsMapper()

    @Provides
    fun providesServicesMapper(): Mapper<ServiceDTO, ServiceDB> = ServicesMapper()

    @Provides
    fun providesTransactionsMapper(): Mapper<TransactionDTO, TransactionDB> = TransactionsMapper()

}