package ru.petroplus.pos.networkworker.data

data class SchedulerConfiguration(
    var id: Long,
    var configurationUpdateInterval: Long = 0,
    var invocationInterval: Long = 0
)