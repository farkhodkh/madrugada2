package ru.petrolplus.pos.room.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.dao.GUIDParamsDao
import ru.petrolplus.pos.room.dao.ReceiptDao
import ru.petrolplus.pos.room.dao.ReceiptParamsDao
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petrolplus.pos.core.MainScreenScope

@Module
class RoomModule {

    @[Provides MainScreenScope]
    fun provideBaseSettingsDao(appDatabase: AppDatabase): BaseSettingsDao = appDatabase.baseSettingsDao()

    @[Provides MainScreenScope]
    fun provideCommonSettingsDao(appDatabase: AppDatabase): CommonSettingsDao = appDatabase.commonSettingsDao()

    @[Provides MainScreenScope]
    fun provideShiftParamsDao(appDatabase: AppDatabase): ShiftParamsDao = appDatabase.shiftParamsDao()

    @[Provides MainScreenScope]
    fun provideGUIDParamsDao(appDatabase: AppDatabase): GUIDParamsDao = appDatabase.guidParamsDao()

    @[Provides MainScreenScope]
    fun provideServicesDao(appDatabase: AppDatabase): ServicesDao = appDatabase.servicesDao()

    @[Provides MainScreenScope]
    fun provideTransactionsDao(appDatabase: AppDatabase): TransactionsDao = appDatabase.transactionDao()

    @[Provides MainScreenScope]
    fun provideReceiptParamsDao(appDatabase: AppDatabase): ReceiptParamsDao = appDatabase.receiptParamsDao()

    @[Provides MainScreenScope]
    fun provideReceiptDao(appDatabase: AppDatabase): ReceiptDao = appDatabase.receiptDao()
}