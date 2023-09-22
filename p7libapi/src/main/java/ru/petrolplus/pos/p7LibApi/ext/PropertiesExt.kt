package ru.petrolplus.pos.p7LibApi.ext

import ru.petrolplus.pos.p7LibApi.dto.InitDataDto
import java.util.Properties

/**
 * Переобразование Map с переметрами в DTO объект
 */
fun Properties.toInitDataDto(): InitDataDto {
    val acquirerId = this.getValue("AcquireID") as String
    val terminalId = this.getValue("TerminalID") as String
    val hostIp = this.getValue("Host1_ip") as String
    val hostPort = this.getValue("Host1_port") as String

    return InitDataDto(
        acquirerId.toInt(),
        terminalId.toInt(),
        hostIp,
        hostPort.toInt()
    )
}