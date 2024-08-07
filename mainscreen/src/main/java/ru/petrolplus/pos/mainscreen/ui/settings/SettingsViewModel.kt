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
import kotlinx.coroutines.launch
import ru.petrolplus.pos.persitence.ServicesPersistence
import ru.petrolplus.pos.persitence.dto.ServicesDTO

class SettingsViewModel(
    private val servicesPersistence: ServicesPersistence,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _viewState = mutableStateOf<SettingsViewState>(SettingsViewState.DebugState)
    val viewState: State<SettingsViewState> = _viewState

    fun addOrReplaceServices(servicesDTO: ServicesDTO) {
        viewModelScope.launch(Dispatchers.IO) {
            servicesPersistence.addOrReplace(servicesDTO.services)
        }
    }

    companion object {
        fun provideFactory(
            servicesPersistence: ServicesPersistence,
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
                    return SettingsViewModel(servicesPersistence, handle) as T
                }
            }
    }
}