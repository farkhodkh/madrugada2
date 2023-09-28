package ru.petrolplus.pos.mainscreen.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Базовая вьюмодель, инкапсулирует и систематизирует логику работы с состояниями
 */
abstract class BaseViewModel<T : ViewState> : ViewModel() {

    /**
     * Абстрактный метод который обязаны реализовать наследники
     * @return стартовое состояние экрана
     */
    abstract fun createInitialState(): T

    /**
     * Хранит текущее состояние экрана. Инициализируется лениво, при первой подписке на элемент
     */
    private val _viewState: MutableStateFlow<T> by lazy { MutableStateFlow(createInitialState()) }

    /**
     * Инкапсулирует получение текущего состояния экрана
     */
    protected val currentState: T = _viewState.value

    /**
     * Служит для запрета менять состояние экрана извне
     */
    val viewState: StateFlow<T> = _viewState

    /**
     * Метод для изменения текущего состояни экрана
     * @param state новое состояние экрана
     */
    protected fun setState(state: T) {
        _viewState.value = state
    }
}