package ru.petrolplus.pos.ui.main

sealed class MainScreenState {
    object StartingState: MainScreenState()
    object CheckingSettingsState: MainScreenState()
    class CheckingSettingsError(val errorMessageId: Int = 0, val errorMessage: String = "") : MainScreenState()
    object NoIniFileError : MainScreenState()
    object DownloadIniFileState : MainScreenState()
    object CheckingSuccessState: MainScreenState()
}