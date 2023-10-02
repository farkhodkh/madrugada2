package ru.petrolplus.pos.sdkapi

import kotlinx.coroutines.flow.Flow
import ru.petrolplus.pos.sdkapi.dto.TerminalDataDto
import ru.petrolplus.pos.sdkapi.tlv.BerTlvs

/**
 * Репозитор для обработки команд и коллекции ответов от Reader
 */
interface ISDKRepository {
    /**
     * Метод для синхронной передачи команды в терминал
     * @param bytesString - Строковое представление передаваемой команды
     */
    fun sendCommandTlv(bytesString: String): BerTlvs?

    /**
     * Метод для синхронной передачи команды в терминал
     * @param bytesString - Строковое представление передаваемой команды
     */
    fun sendCommandSync(bytesString: String)

    /**
     * Метод для ассинхронной передачи команды в терминал
     * @param bytesString - Строковое представление передаваемой команды
     */
    fun sendCommand(bytesString: String): ByteArray

    /**
     *  Flow для обработки команд от терминала
     */
    val latestCommands: Flow<TerminalDataDto>
}
