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
import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO
import ru.petrolplus.pos.persitence.dto.CommonSettingsDTO
import ru.petrolplus.pos.persitence.dto.GUIDParamsDTO
import ru.petrolplus.pos.persitence.dto.ServiceDTO
import ru.petrolplus.pos.persitence.dto.ShiftParamsDTO
import ru.petrolplus.pos.persitence.dto.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.dao.GUIDparamsDao
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.GUIDParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB
import ru.petroplus.pos.core.MainScreenScope

@Module
class PersistenceModule {

    @[Provides MainScreenScope]
    fun providesStoreStrategy(): StoreStrategy = DatabaseStoreStrategy()

    @[Provides MainScreenScope]
    fun provideBaseSettingsPersistence(
        baseSettingsDao: BaseSettingsDao,
        mapper: Mapper<BaseSettingsDTO, BaseSettingsDB>,
        storeStrategy: StoreStrategy
    ): BaseSettingsPersistence = BaseSettingsPersistenceImpl(baseSettingsDao, mapper, storeStrategy)

    @[Provides MainScreenScope]
    fun provideCommonSettingsPersistence(
        commonSettingsDao: CommonSettingsDao,
        mapper: Mapper<CommonSettingsDTO, CommonSettingsDB>,
        storeStrategy: StoreStrategy
    ): CommonSettingsPersistence =
        CommonSettingsPersistenceImpl(commonSettingsDao, mapper, storeStrategy)

    @[Provides MainScreenScope]
    fun provideGUIDparamsPersistence(
        guidParamsDao: GUIDparamsDao,
        mapper: Mapper<GUIDParamsDTO, GUIDParamsDB>,
        storeStrategy: StoreStrategy
    ): GUIDparamsPersistence = GUIDparamsPersistenceImpl(guidParamsDao, mapper, storeStrategy)

    @[Provides MainScreenScope]
    fun provideShiftParamsPersistence(
        shiftParamsDao: ShiftParamsDao,
        mapper: Mapper<ShiftParamsDTO, ShiftParamsDB>,
        storeStrategy: StoreStrategy
    ): ShiftParamsPersistence = ShiftParamsPersistenceImpl(shiftParamsDao, mapper, storeStrategy)

    @[Provides MainScreenScope]
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

    @[Provides MainScreenScope]
    fun provideServicesPersistence(
        servicesDao: ServicesDao,
        mapper: Mapper<ServiceDTO, ServiceDB>
    ): ServicesPersistence = ServicesPersistenceImpl(servicesDao, mapper)

    @[Provides MainScreenScope]
    fun provideTransactionsPersistence(
        transactionsDao: TransactionsDao,
        mapper: Mapper<TransactionDTO, TransactionDB>
    ): TransactionsPersistence = TransactionsPersistenceImpl(transactionsDao, mapper)
}