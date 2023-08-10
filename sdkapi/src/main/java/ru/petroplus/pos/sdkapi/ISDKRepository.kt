package ru.petroplus.pos.sdkapi

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
     * EventBus для обработки команд от терминала
     * TODO - переписать под Flow
     */
    var eventBus: ReaderEventBus
}