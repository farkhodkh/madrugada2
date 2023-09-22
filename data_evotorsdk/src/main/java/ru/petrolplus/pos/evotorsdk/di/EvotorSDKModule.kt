package ru.petrolplus.pos.evotorsdk.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.evotorsdk.EvotorSDKRepository
import ru.petrolplus.pos.sdkapi.ISDKRepository

@Module
class EvotorSDKModule {
    @[Provides AppScope]
    fun providesEvotorSDKRepository(context: Context): ISDKRepository = EvotorSDKRepository(context)
}
