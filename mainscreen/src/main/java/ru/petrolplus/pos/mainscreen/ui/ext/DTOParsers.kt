package ru.petrolplus.pos.mainscreen.ui.ext

import ru.petrolplus.pos.p7LibApi.dto.InitDataDto
import ru.petrolplus.pos.persitence.dto.BaseSettingsDTO

fun BaseSettingsDTO.toInitDataDto(): InitDataDto = InitDataDto(
    acquirerId = this.acquirerId,
    terminalId = this.terminalId,
    hostIp = this.hostIp,
    hostPort = this.hostPort,
)