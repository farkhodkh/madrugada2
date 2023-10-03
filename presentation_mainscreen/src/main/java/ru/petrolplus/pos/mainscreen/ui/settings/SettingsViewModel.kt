package ru.petrolplus.pos.mainscreen.ui.settings

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.Dispatchers
import ru.petrolplus.pos.core.errorhandling.launchHandling
import ru.petrolplus.pos.persitence.ServicesPersistence
import ru.petrolplus.pos.persitence.dto.ServicesDTO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.petrolplus.pos.mainscreen.ui.BaseViewModel

class SettingsViewModel @AssistedInject constructor(
    private val servicesPersistence: ServicesPersistence,
    @Assisted private val savedStateHandle: SavedStateHandle
): BaseViewModel<SettingsViewState>() {

    override fun createInitialState(): SettingsViewState = SettingsViewState.DebugState

    fun addOrReplaceServices(servicesDTO: ServicesDTO) {
        viewModelScope.launchHandling(Dispatchers.IO) {
            servicesPersistence.addOrReplace(servicesDTO.services)
        }
    }

    @AssistedFactory
    interface SettingsViewModelFactory {
        fun create(savedStateHandle: SavedStateHandle): SettingsViewModel
    }

    companion object {
        fun provideFactory(
            factory: SettingsViewModelFactory,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return factory.create(handle) as T
                }
            }
    }
}