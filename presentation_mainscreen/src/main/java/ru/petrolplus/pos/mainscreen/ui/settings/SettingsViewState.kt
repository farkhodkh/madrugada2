package ru.petrolplus.pos.mainscreen.ui.settings

import ru.petrolplus.pos.mainscreen.ui.ViewState

sealed class SettingsViewState : ViewState {
    object DebugState : SettingsViewState()
}