package ru.petroplus.pos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.evotorprinter.EvotorDocumentInflater
import ru.petroplus.pos.evotorprinter.EvotorPrinterApiImpl
import ru.petroplus.pos.evotorsdk.EvotorSDKRepository
import ru.petroplus.pos.p7Lib.impl.P7LibCallbacksImpl
import ru.petroplus.pos.p7Lib.impl.P7LibRepositoryImpl
import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.printerapi.PrinterApi
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.sdkapi.ISDKRepository
import ru.petroplus.pos.ui.main.MainActivityViewModel

@Module
object AppModule {
    @[Provides AppScope]
    fun providesP7LibRepository(): IP7LibRepository = P7LibRepositoryImpl()

    @[Provides AppScope]
    fun providesP7LibCallbacks(): IP7LibCallbacks = P7LibCallbacksImpl()

    @[Provides AppScope]
    fun providePrinter(context: Context): PrinterApi = EvotorPrinterApiImpl(context, EvotorDocumentInflater())


    @[Provides AppScope]
    fun providesMainActivityViewModel(
        repository: IP7LibRepository,
        callBacks: IP7LibCallbacks
    ): MainActivityViewModel = MainActivityViewModel(repository, callBacks)

    @[Provides AppScope]
    fun providesEvotorSDKRepository(context: Context): ISDKRepository = EvotorSDKRepository(context)

    @[Provides AppScope]
    fun providesCardReaderRepository(sdkRepository: ISDKRepository): CardReaderRepository =
        object : CardReaderRepository {
            override val sdkRepository: ISDKRepository
                get() = sdkRepository
        }


    @[Provides AppScope]
    fun providesAppDatabase(context: Context) = AppDatabase.getInstance(context)
}