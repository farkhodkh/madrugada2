package ru.petroplus.pos.sdkapi

import kotlinx.coroutines.flow.Flow

/**
 * Репозитор для обработки команд и коллекции ответов от Reader
 */
interface ISDKRepository {
    /**
     * Метод для передачи команды в терминал
     * @param bytesString - Строковое представление передаваемой команды
     */
    fun sendCommand(bytesString: String)

    /**
     *  Flow для обработки команд от терминала
     */
    val latestCommands: Flow<String>
}