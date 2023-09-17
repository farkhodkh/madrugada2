package ru.petrolplus.pos.evotorsdk.util

/**
 * Класс для перечисления тегов для команд TLV
 * Описание тегов из оригинального описания API
 */
sealed class TlvTags(val code: Int, val codeStr: String, val defValue: String = "") {
    /**
     * Card found: 0:None, 1:MS, 2:ICC, 4:CLess
     */
    object CardFind: TlvTags(1, "01", "02")

    /**
     * ATR if present
     */
    object Atr: TlvTags(2, "02")

    /**
     * CLA INS P1 P2 Lc <DataCmd> Le – для ICC и NFC интерфейсов
     */
    object Apdu: TlvTags(3, "03")

    /**
     * DataAnswer
     */
    object DataAnswer: TlvTags(4, "04")

    /**
     * SW от карты
     */
    object SW: TlvTags(5, "05")

    /**
     * PinMode: 1:OnLine, 2:OfflinePlaintext, 3:OfflineEnc
     */
    object PinMode: TlvTags(6, "06")

    /**
     * Random. 8 бинарных байт случайного числа,  созданных СМ.
     */
    object Random: TlvTags(7, "07")

    /**
     * OnLine PIN block
     */
    object OnLinePin: TlvTags(10,"0A")

    /**
     * Check общего ключа
     */
    object CheckKey: TlvTags(11,"0В")

    /**
     * Delete key (длина тэга = 0)
     * Plaintext KLK
     * RKL:RSA encrypted KLK. Размер тэга 256 является признаком использования RKL
     */
    object DeleteKey: TlvTags(12, "0C")

    /**
     * Check общего ключа. Если отсутствует – ключ не согласован
     */
    object KLKKey: TlvTags(13, "0D")

    /**
     * Data for encrypt
     */
    object DataForEncrypt: TlvTags(14, "0E")

    /**
     * Data encrypted 3DES ECB
     */
    object DataEncrypted: TlvTags(15, "0F")

    /**
     * МЭ Serial
     */
    object MeSerial: TlvTags(16, "10")

    /**
     * МЭ Model
     */
    object MeModel: TlvTags(17, "11")

    /**
     * МЭ Version
     */
    object MeVersion: TlvTags(18, "12")

    /**
     * МЭ Keys check list [<Name(4)>:<CHK(6)>,]
     */
    object MeKeys: TlvTags(19, "13")

    /**
     * МЭ Status
     */
    object MeStatus: TlvTags(20, "14")

    /**
     * Tamper state string
     */
    object TamperState: TlvTags(21, "15")

    /**
     * RKL:Signature. Присутствует если KLK передается через RKL
     */
    object RKLSignature: TlvTags(22, "16")

    /**
     * RKL:Random. Присутствует если KLK передается через RKL.
     * Если отсутствует - будет взято значение, созданное предыдущей командой 0x0B
     */
    object RKLRandom: TlvTags(23, "17")

    /**
     * Поле CN из сертификата RKL
     */
    object CNField: TlvTags(24, "18")

    /**
     * RSA Random nonce (Присутствует для Offline Encrypted PIN)
     */
    object RSARandom: TlvTags(26, "1A")

    /**
     * Сертификат устройства в DER виде
     */
    object DerCertificate: TlvTags(32, "20")

    /**
     * МЭ Random 24 byte
     */
    object MeRandom: TlvTags(33, "21")


    /**
     * Шифрованный на сертификате устройства 24 МЭ random + 24 Random библиотеки
     */
    object MeRandomEncrypted: TlvTags(34, "22")

    /**
     * Зашифрованный Offline PIN block
     */
    object OfflinePin: TlvTags(42, "2A")

    /**
     * New KLK under Old KLK
     */
    object NewKLK : TlvTags(64, "40")

    /**
     * MPIN under KLK
     */
    object MPIN : TlvTags(65, "41")

    /**
     * MMAC under KLK
     */
    object MMAC : TlvTags(66, "42")

    /**
     * MTDK under KLK
     */
    object MTDK : TlvTags(67, "43")

    /**
     * SPIN under MPIN
     */
    object SPIN : TlvTags(72, "48")

    /**
     * SMAC under MMAC
     */
    object SMAC : TlvTags(73, "49")

    /**
     * STDK under MTDK – не используется
     */
    object STDK: TlvTags(74, "4A")

    /**
     * SENC under MMAC
     */
    object SENC: TlvTags(75, "4B")

    /**
     * Track2 – если обнаружена магнитная карта
     */
    object Track2: TlvTags(82, "52")

    /**
     * Track1 – если обнаружена магнитная карта
     */
    object Track1: TlvTags(86, "56")

    /**
     * Full PAN
     */
    object PAN: TlvTags(90, "5A")

    /**
     * Amount
     */
    object Amount: TlvTags(40706, "9F02")

    /**
     * Amount 2
     */
    object Amount2: TlvTags(40707, "9F03")

    /**
     * Full ICC public key (module) (Присутствует для Offline Encrypted PIN)
     */
    object PublicKey: TlvTags(40749, "9F2D")

    /**
     * ICC Exponent (Присутствует для Offline Encrypted PIN)
     */
    object ICCExponent: TlvTags(40750, "9F2E")
}