package ru.petrolplus.pos.p7LibApi

import ru.petrolplus.pos.p7LibApi.requests.ApduData
import ru.petrolplus.pos.p7LibApi.responces.ApduAnswer

/**
 * Интерфейс для вызова колл-беков при входящих колл-беках от p7lib
 */
interface OnP7LibResultListener {
    fun onCardReset(answer: ApduAnswer): String?

    fun onSendDataToCard(data: ApduData, answer: ApduAnswer): ByteArray?
}