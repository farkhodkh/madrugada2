package ru.petrolplus.pos.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.p7Lib.impl.P7LibCallbacksImpl
import ru.petrolplus.pos.p7Lib.impl.P7LibRepositoryImpl
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.sdkapi.CardReaderRepository
import ru.petrolplus.pos.sdkapi.ISDKRepository

@Module
object AppModule {
    // TODO: вынести providesP7LibRepository & providesP7LibCallbacks
    @[Provides AppScope]
    fun providesP7LibRepository(): IP7LibRepository = P7LibRepositoryImpl()

    @[Provides AppScope]
    fun providesP7LibCallbacks(): IP7LibCallbacks = P7LibCallbacksImpl()

    @[Provides AppScope]
    fun providesCardReaderRepository(sdkRepository: ISDKRepository): CardReaderRepository =
        object : CardReaderRepository {
            override val sdkRepository: ISDKRepository
                get() = sdkRepository
        }
}