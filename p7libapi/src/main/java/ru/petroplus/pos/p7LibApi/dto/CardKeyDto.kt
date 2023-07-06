package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс задаёт ключи для построения и шифрования PIN блока
 * TODO - Юрий добавь описание
 * @property publicKey -
 * @property publicExt -
 * @property nonce -
 * @author - @FAHA
 */
class CardKeyDto(
    var publicKey: ByteArray = byteArrayOf(),
    var publicExt: ByteArray = byteArrayOf(),
    var nonce: ByteArray = byteArrayOf()
)