package ru.petrolplus.pos.mainscreen.ui.settings.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import ru.petrolplus.pos.core.SettingsScreenScope

@Subcomponent(modules = [SettingsScreenModule::class])
@SettingsScreenScope
interface SettingsScreenComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance owner: SavedStateRegistryOwner,
        ): SettingsScreenComponent
    }

    fun getViewModelFactory(): AbstractSavedStateViewModelFactory
}