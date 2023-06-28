package ru.petroplus.pos.ui.main

sealed class MainScreenState {
    object StartingState: MainScreenState()
    object CheckingSettingsState: MainScreenState()
    class CheckingSettingsError(val errorMessageId: Int) : MainScreenState()
    object NoIniFileError : MainScreenState()
    object DownloadIniFileState : MainScreenState()
    object CheckingSuccessState: MainScreenState()
}