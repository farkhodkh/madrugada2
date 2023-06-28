package ru.petroplus.pos.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.petroplus.pos.R
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import java.io.File
import kotlin.system.exitProcess


class MainActivityViewModel(
    private val p7LibraryRepository: IP7LibRepository
) : ViewModel() {

    val _viewState: MutableStateFlow<MainScreenState> = MutableStateFlow(MainScreenState.StartingState)
    val viewState: StateFlow<MainScreenState> = _viewState
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), MainScreenState.StartingState)

//    private var _viewState: MutableLiveData<MainScreenState> = MutableLiveData(MainScreenState.StartingState)
//    val viewState: LiveData<MainScreenState> = _viewState

    private var cacheDir: File? = null

    init {
        viewModelScope.launch {
            delay(2000)
            _viewState.value = MainScreenState.CheckingSettingsState
        }
    }

    private fun initLibrary() {

    }

    private fun readIniFile() {
        cacheDir?.let { dir ->
            if (dir.exists()) {
                val iniFile = dir
                    .listFiles()
                    ?.filter { it.isFile && it.name.endsWith(".ini") }

                if (iniFile.isNullOrEmpty()) {
                    _viewState.value = MainScreenState.NoIniFileError
                } else {
                    //_viewState.value = MainScreenState.NoIniFileError
                }
            }
        }
    }

    fun setCacheDir(cacheDir: File?) {
        cacheDir?.let {
            this.cacheDir = it
            readIniFile()
        } ?: run {
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

    fun configurationFileDownloaded() {
        _viewState.value = MainScreenState.CheckingSuccessState
    }
}