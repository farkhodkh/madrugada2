package ru.petrolplus.pos.mainscreen.ui.settings.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.core.SettingsScreenScope
import ru.petrolplus.pos.mainscreen.ui.settings.SettingsViewModel
import ru.petrolplus.pos.persitence.ServicesPersistence

@Module
class SettingsScreenModule {

    @Provides
    @SettingsScreenScope
    fun provideSettingsViewModelFactory(
        servicesPersistence: ServicesPersistence,
        owner: SavedStateRegistryOwner
    ): AbstractSavedStateViewModelFactory {
        return SettingsViewModel.provideFactory(servicesPersistence, owner)
    }
}
