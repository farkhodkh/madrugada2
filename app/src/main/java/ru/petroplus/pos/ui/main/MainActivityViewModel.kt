package ru.petroplus.pos.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.petroplus.pos.R
import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.p7LibApi.dto.OK
import ru.petroplus.pos.util.ConfigurationFileReader
import ru.petroplus.pos.util.constants.Constants.CONFIG_FILE_NAME
import ru.petroplus.pos.util.ext.toInitDataDto
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import kotlin.system.exitProcess

class MainActivityViewModel(
    private val p7LibraryRepository: IP7LibRepository,
    private val callbacks: IP7LibCallbacks
) : ViewModel() {

    private val configurationReaderUtil by lazy { ConfigurationFileReader() }

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

    fun readConfigurationFile(confFile: FileInputStream?) {
        if (confFile == null) {
            _viewState.value =
                MainScreenState.NoIniFileError
            return
        }

        try {
            configurationReaderUtil
                .readConfigurationFileContent(confFile)
        } catch (ex: ConfigurationFileReader.ConfigurationFileReaderException) {
            _viewState.value =
                MainScreenState.CheckingSettingsError(errorMessageId = R.string.cache_dir_access_error)
            return
        }

        val result = p7LibraryRepository.init(
            configurationReaderUtil.properties.toInitDataDto(),
            "",
            callbacks,
            "",
            ""
        )

        if (result.code == OK.code) {
            _viewState.value =
                MainScreenState.CheckingSuccessState
        } else {
            _viewState.value =
                MainScreenState.CheckingSettingsError(R.string.cache_dir_access_error)
        }
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
                MainScreenState.CheckingSettingsError(errorMessage = ex.localizedMessage.toString())
        }

    }
}