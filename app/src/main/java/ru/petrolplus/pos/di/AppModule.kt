package ru.petrolplus.pos.di

import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.p7Lib.impl.P7LibCallbacksImpl
import ru.petrolplus.pos.p7Lib.impl.P7LibRepositoryImpl
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository

@Module
object AppModule {
    // TODO: вынести providesP7LibRepository & providesP7LibCallbacks
    // "P7Lib будет переписываться. Пусть provides будут пока здесь"  © Фарход
    @[Provides AppScope]
    fun providesP7LibRepository(): IP7LibRepository = P7LibRepositoryImpl()

    @[Provides AppScope]
    fun providesP7LibCallbacks(gatewayServerRepository: GatewayServerRepositoryApi): IP7LibCallbacks = P7LibCallbacksImpl(gatewayServerRepository)
}