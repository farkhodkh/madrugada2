package ru.petrolplus.pos.networkworker.model

/**
 * Действия для планировщика
 */
sealed class GatewayAction {
    /**
     * Инициализауия соединение со шлюзом
     */
    object Init : GatewayAction()

    /**
     * Отправка Ping c OOB
     */
    object Ping : GatewayAction()
}
