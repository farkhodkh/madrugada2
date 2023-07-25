package ru.petroplus.pos.ui.main

import android.content.Context
import android.os.Environment
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
import ru.petroplus.pos.p7LibApi.dto.*
import ru.petroplus.pos.p7LibApi.dto.card.CardInfo
import ru.petroplus.pos.p7LibApi.dto.card.P7CardInfo
import ru.petroplus.pos.util.ConfigurationFileReader
import ru.petroplus.pos.util.constants.Constants.CONFIG_FILE_NAME
import ru.petroplus.pos.util.ext.toInitDataDto
import java.io.File
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


        var UUID = TransactionUUIDDto()
        var cardKey = CardKeyDto()
        var cardData = P7CardInfo()

        var debitParams = DebitParamsDto()
        var transInfo = TransactionInfoDto()
        var refundParam = RefundParamsDto()
        var errorInfo = ErrorInfoDto()
        var libInfo = LibInfoDto()

        UUID.onlineTransNumber = 18
        UUID.lastGenTime = 1690547808
        UUID.clockSequence = 61920
        UUID.hasNodeId = true
        UUID.nodeId = "01B5146FB4E3"

        var DataDirectoryPath = String()

//        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            DataDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();  }
        DataDirectoryPath = Environment.getStorageDirectory().getAbsolutePath();
        DataDirectoryPath += File.separator + "P7Lib";

        var result = p7LibraryRepository.init(
            configurationReaderUtil.properties.toInitDataDto(),
            UUID,
            callbacks,
            DataDirectoryPath,
            DataDirectoryPath
        )

        if (result.code != OK.code) {
            throw RuntimeException("first init fail!")
        }
        result = p7LibraryRepository.deInit()
        if (result.code != OK.code) {
            throw RuntimeException("deInit fail!")
        }
        result = p7LibraryRepository.init(
            configurationReaderUtil.properties.toInitDataDto(),
            UUID,
            callbacks,
            DataDirectoryPath,
            DataDirectoryPath
        )
        if (result.code != OK.code) {
            throw RuntimeException("second init fail!")
        }
        result = p7LibraryRepository.detect(cardKey, cardData)
        if (result.code != OK.code) {
            throw RuntimeException("detect fail!")
        }
        debitParams.serviceWhat = 1
        debitParams.serviceFrom = 0
        debitParams.amount = 100u
        debitParams.price  = 1000u
        debitParams.sum    = 1000u
        debitParams.pinBlock = ubyteArrayOf(0xFAu, 0xCEu, 0xBEu, 0xF0u, 0xE7u).toByteArray()
        result = p7LibraryRepository.debit(debitParams, transInfo, UUID)
        if (result.code != OK.code) {
            throw RuntimeException("debit fail!")
        }
        refundParam.serviceWhat = debitParams.serviceWhat
        refundParam.price       = debitParams.price
        refundParam.amount      = debitParams.amount
        refundParam.sum         = debitParams.sum
        result = p7LibraryRepository.refund(refundParam, transInfo, UUID)
        if (result.code != OK.code) {
            throw RuntimeException("refund fail!")
        }
        result = p7LibraryRepository.getErrorInfo(errorInfo)
        if (result.code != OK.code) {
            throw RuntimeException("getErrorInfo fail!")
        }
        result = p7LibraryRepository.getLibInfo(libInfo)
        if (result.code != OK.code) {
            throw RuntimeException("getLibInfo fail!")
        }

        

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
                MainScreenState.CheckingSettingsError(errorMessage = ex.localizedMessage.orEmpty())
        }

    }
}