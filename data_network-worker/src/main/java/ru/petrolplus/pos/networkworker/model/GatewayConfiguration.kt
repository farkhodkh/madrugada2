package ru.petrolplus.pos.networkworker.model

/**
 * Конфигурация для работы со шлюзом
 * @property version - версия конфигураци
 * @property actions - Коллекция выполняемых действий для executor
 */
class GatewayConfiguration(val version: String, val actions: Collection<GatewayAction>) {
    companion object Versions {
        val VERSION_1 = "0001"
    }
}
