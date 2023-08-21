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
import ru.petrolplus.pos.persitence.ReceiptParamsPersistence
import ru.petrolplus.pos.persitence.ReceiptParamsPersistenceImpl
import ru.petrolplus.pos.persitence.ReceiptPersistence
import ru.petrolplus.pos.persitence.ReceiptPersistenceImpl
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
import ru.petrolplus.pos.persitence.entities.DebitReceiptDTO
import ru.petrolplus.pos.persitence.entities.GUIDParamsDTO
import ru.petrolplus.pos.persitence.entities.ReceiptParamsDTO
import ru.petrolplus.pos.persitence.entities.ServiceDTO
import ru.petrolplus.pos.persitence.entities.ShiftParamsDTO
import ru.petrolplus.pos.persitence.entities.TransactionDTO
import ru.petrolplus.pos.persitence.mappers.Mapper
import ru.petrolplus.pos.persitence.mappers.ProjectionMapper
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.dao.GUIDparamsDao
import ru.petrolplus.pos.room.dao.ReceiptDao
import ru.petrolplus.pos.room.dao.ReceiptParamsDao
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.dao.TransactionsDao
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
        shiftParamsPersistence: ShiftParamsPersistence,
        receiptParamsPersistence: ReceiptParamsPersistence
    ): SettingsPersistence = SettingsPersistenceImpl(
        baseSettingsPersistence,
        commonSettingsPersistence,
        guiDparamsPersistence,
        shiftParamsPersistence,
        receiptParamsPersistence
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

    @[Provides MainScreenScope]
    fun provideReceiptParamsPersistence(
        receiptParamsDao: ReceiptParamsDao,
        mapper: Mapper<ReceiptParamsDTO, ReceiptParamsDB>,
        storeStrategy: StoreStrategy
    ): ReceiptParamsPersistence = ReceiptParamsPersistenceImpl(receiptParamsDao, mapper, storeStrategy)

    @[Provides MainScreenScope]
    fun provideReceiptMapper(
        receiptDao: ReceiptDao,
        mapper: ProjectionMapper<ReceiptProjection, DebitReceiptDTO>
    ): ReceiptPersistence = ReceiptPersistenceImpl(receiptDao, mapper)
}