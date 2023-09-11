package ru.petrolplus.pos.mainscreen.ui.debit.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import dagger.BindsInstance
import dagger.Subcomponent
import ru.petrolplus.pos.core.SettingsScreenScope

@Subcomponent(modules = [DebitScreenModule::class])
@SettingsScreenScope
interface DebitScreenComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance owner: SavedStateRegistryOwner): DebitScreenComponent
    }

    fun getViewModelFactory(): AbstractSavedStateViewModelFactory
}
