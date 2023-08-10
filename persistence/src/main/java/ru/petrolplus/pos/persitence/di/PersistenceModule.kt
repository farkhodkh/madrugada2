package ru.petrolplus.pos.persitence.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.persitence.BaseSettingsPersistence
import ru.petrolplus.pos.persitence.BaseSettingsPersistenceImpl
import ru.petrolplus.pos.persitence.CommonSettingsPersistence
import ru.petrolplus.pos.persitence.CommonSettingsPersistenceImpl
import ru.petrolplus.pos.persitence.DatabaseStoreStrategy
import ru.petrolplus.pos.persitence.GUIDparamsPersistence
import ru.petrolplus.pos.persitence.GUIDparamsPersistenceImpl
import ru.petrolplus.pos.persitence.ServicesPersistence
import ru.petrolplus.pos.persitence.ServicesPersistenceImpl
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.SettingsPersistenceImpl
import ru.petrolplus.pos.persitence.ShiftParamsPersistence
import ru.petrolplus.pos.persitence.ShiftParamsPersistenceImpl
import ru.petrolplus.pos.persitence.StoreStrategy
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistenceImpl
import ru.petrolplus.pos.persitence.entities.BaseSettingsDTO
import ru.petrolplus.pos.persitence.entities.CommonSettingsDTO
import ru.petrolplus.pos.persitence.entities.GUIDparamsDTO
import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petrolplus.pos.persitence.entities.ShiftParamsDTO
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.dao.GUIDparamsDao
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.GUIDparamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB

@Module
class PersistenceModule {

    @Provides
    fun providesStoreStrategy(): StoreStrategy = DatabaseStoreStrategy()

    @Provides
    fun provideBaseSettingsPersistence(
        baseSettingsDao: BaseSettingsDao,
        mapper: Mapper<BaseSettingsDTO, BaseSettingsDB>,
        storeStrategy: StoreStrategy
    ): BaseSettingsPersistence = BaseSettingsPersistenceImpl(baseSettingsDao, mapper, storeStrategy)

    @Provides
    fun provideCommonSettingsPersistence(
        commonSettingsDao: CommonSettingsDao,
        mapper: Mapper<CommonSettingsDTO, CommonSettingsDB>,
        storeStrategy: StoreStrategy
    ): CommonSettingsPersistence =
        CommonSettingsPersistenceImpl(commonSettingsDao, mapper, storeStrategy)

    @Provides
    fun provideGUIDparamsPersistence(
        guidParamsDao: GUIDparamsDao,
        mapper: Mapper<GUIDparamsDTO, GUIDparamsDB>,
        storeStrategy: StoreStrategy
    ): GUIDparamsPersistence = GUIDparamsPersistenceImpl(guidParamsDao, mapper, storeStrategy)

    @Provides
    fun provideShiftParamsPersistence(
        shiftParamsDao: ShiftParamsDao,
        mapper: Mapper<ShiftParamsDTO, ShiftParamsDB>,
        storeStrategy: StoreStrategy
    ): ShiftParamsPersistence = ShiftParamsPersistenceImpl(shiftParamsDao, mapper, storeStrategy)

    @Provides
    fun providesSettingsPersistence(
        baseSettingsPersistence: BaseSettingsPersistence,
        commonSettingsPersistence: CommonSettingsPersistence,
        guiDparamsPersistence: GUIDparamsPersistence,
        shiftParamsPersistence: ShiftParamsPersistence
    ): SettingsPersistence = SettingsPersistenceImpl(
        baseSettingsPersistence,
        commonSettingsPersistence,
        guiDparamsPersistence,
        shiftParamsPersistence
    )

    @Provides
    fun provideServicesPersistence(
        servicesDao: ServicesDao,
        mapper: Mapper<ServiceDTO, ServiceDB>
    ): ServicesPersistence = ServicesPersistenceImpl(servicesDao, mapper)

    @Provides
    fun provideTransactionsPersistence(
        transactionsDao: TransactionsDao,
        mapper: Mapper<TransactionDTO, TransactionDB>
    ): TransactionsPersistence = TransactionsPersistenceImpl(transactionsDao, mapper)
}