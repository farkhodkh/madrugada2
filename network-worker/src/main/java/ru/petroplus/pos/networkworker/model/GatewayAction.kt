package ru.petroplus.pos.networkworker.model

sealed class GatewayAction {
    /**
     * Инициализауия соединение со шлюзом
     */
    object Init: GatewayAction()

    /**
     * Отправка Ping c OOB
     */
    object Ping: GatewayAction()

    /**
     * Отправка данных от P7Lib
     */
    object SendData: GatewayAction()
}