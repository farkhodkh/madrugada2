package ru.petrolplus.pos.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO
import ru.petrolplus.pos.R
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.util.ConfigurationFileReader
import ru.petrolplus.pos.util.constants.Constants.CONFIG_FILE_NAME
import ru.petrolplus.pos.util.ext.toInitDataDto
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.system.exitProcess

class MainActivityViewModel(
    private val p7LibraryRepository: IP7LibRepository,
    private val configurationFileReader: ConfigurationFileReader,
    private val settingsPersistence: SettingsPersistence,
    private val callbacks: IP7LibCallbacks
) : ViewModel() {

    private val _viewState: MutableStateFlow<MainScreenState> =
        MutableStateFlow(MainScreenState.StartingState)
    val viewState: StateFlow<MainScreenState> = _viewState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainScreenState.StartingState)

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.value = MainScreenState.CheckingSettingsState
        }
    }

    fun setupConfiguration(configFileName: String) {

        val properties = try {
            configurationFileReader.readConfiguration(configFileName)
        } catch (e: Exception) {
            when(e) {
                is ConfigurationFileReader.NoConfigurationFileException -> {
                    _viewState.value = MainScreenState.NoIniFileError
                }
                is ConfigurationFileReader.FileParseException -> {
                    _viewState.value = MainScreenState.CheckingSettingsError(errorMessageId = R.string.cache_dir_access_error)
                }
            }
            return
        }

        val initDataDto = properties.toInitDataDto()

        viewModelScope.launch {
            settingsPersistence.setBaseSettings(
                BaseSettingsDTO(
                    acquirerId = initDataDto.acquirerId,
                    terminalId = initDataDto.terminalId,
                    hostIp = initDataDto.hostIp,
                    hostPort = initDataDto.hostPort
                )
            )
        }

        _viewState.value = MainScreenState.CheckingSuccessState
    }

    fun configurationFileDownloadRequired() {
        _viewState.value = MainScreenState.DownloadIniFileState
    }

    fun configurationDidnotLoaded() {
        exitProcess(0)
    }

    fun configurationFileDownloaded(baseContext: Context, configurationContent: String) {

        try {
            val filename = CONFIG_FILE_NAME
            baseContext.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(configurationContent.toByteArray())
            }
            _viewState.value = MainScreenState.CheckingSuccessState

        } catch (ex: FileNotFoundException) {
            _viewState.value =
                MainScreenState.CheckingSettingsError(R.string.cache_dir_access_error)
        } catch (ex: IOException) {
            _viewState.value =
                MainScreenState.CheckingSettingsError(errorMessage = ex.localizedMessage.orEmpty())
        }

    }
}