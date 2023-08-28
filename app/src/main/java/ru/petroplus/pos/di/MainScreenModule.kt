package ru.petroplus.pos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petroplus.pos.core.MainScreenScope
import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.ui.main.MainActivityViewModel
import ru.petroplus.pos.util.ConfigurationFileReader

@Module
class MainScreenModule {

    @[Provides MainScreenScope]
    fun provideConfigurationFileReader(context: Context): ConfigurationFileReader {
        return ConfigurationFileReader(context)
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