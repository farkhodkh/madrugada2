package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс задаёт ключи для построения и шифрования PIN блока
 * @property publicKey - публичный ключ
 * @property publicExp - экспанетно публичного ключа
 * @property nonce - случайное число публичного ключа
 * @author - @FAHA
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class CardKeyDto(
    var publicKey: ByteArray = byteArrayOf(),
    var publicExp: ByteArray = byteArrayOf(),
    var nonce: ByteArray = byteArrayOf()
)