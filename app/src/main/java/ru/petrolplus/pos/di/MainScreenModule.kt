package ru.petrolplus.pos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.core.MainScreenScope
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.sdkapi.CardReaderRepository
import ru.petrolplus.pos.sdkapi.ISDKRepository
import ru.petrolplus.pos.ui.main.MainActivityViewModel
import ru.petrolplus.pos.util.ConfigurationFileReader

@Module
class MainScreenModule {

    @[Provides MainScreenScope]
    fun provideConfigurationFileReader(context: Context): ConfigurationFileReader {
        return ConfigurationFileReader(context)
    }


    @[Provides]
    fun providesCardReaderRepository(sdkRepository: ISDKRepository): CardReaderRepository =
        object : CardReaderRepository {
            override val sdkRepository: ISDKRepository
                get() = sdkRepository
        }

    @[Provides MainScreenScope]
    fun providesMainActivityViewModel(
        repository: IP7LibRepository,
        configurationFileReader: ConfigurationFileReader,
        settingsPersistence: SettingsPersistence,
        callBacks: IP7LibCallbacks
    ): MainActivityViewModel = MainActivityViewModel(
        p7LibraryRepository = repository,
        configurationFileReader = configurationFileReader,
        settingsPersistence = settingsPersistence,
        callbacks = callBacks)
}