package ru.petroplus.pos.evotorsdk.util

/**
 * Класс для перечисления тегов для команд TLV
 * Описание тегов из оригинального описания API
 */
sealed class TlvTags(val code: String) {
    /**
     * Card found: 0:None, 1:MS, 2:ICC, 4:CLess
     */
    object CardFind: TlvTags("01")

    /**
     * ATR if present
     */
    object Atr: TlvTags("02")

    /**
     * CLA INS P1 P2 Lc <DataCmd> Le – для ICC и NFC интерфейсов
     */
    object Cla: TlvTags("03")

    /**
     * DataAnswer
     */
    object DataAnswer: TlvTags("04")

    /**
     * SW от карты
     */
    object SW: TlvTags("05")

    /**
     * PinMode: 1:OnLine, 2:OfflinePlaintext, 3:OfflineEnc
     */
    object PinMode: TlvTags("06")

    /**
     * Random. 8 бинарных байт случайного числа,  созданных СМ.
     */
    object Random: TlvTags("07")

    /**
     * OnLine PIN block
     */
    object OnLinePin: TlvTags("0A")

    /**
     * Check общего ключа
     */
    object CheckKey: TlvTags("0В")

    /**
     * Delete key (длина тэга = 0)
     * Plaintext KLK
     * RKL:RSA encrypted KLK. Размер тэга 256 является признаком использования RKL
     */
    object DeleteKey: TlvTags("0C")

    /**
     * Check общего ключа. Если отсутствует – ключ не согласован
     */
    object KLKKey: TlvTags("0D")

    /**
     * Data for encrypt
     */
    object DataForEncrypt: TlvTags("0E")

    /**
     * Data encrypted 3DES ECB
     */
    object DataEncrypted: TlvTags("0F")

    /**
     * МЭ Serial
     */
    object MeSerial: TlvTags("10")

    /**
     * МЭ Model
     */
    object MeModel: TlvTags("11")

    /**
     * МЭ Version
     */
    object MeVersion: TlvTags("12")

    /**
     * МЭ Keys check list [<Name(4)>:<CHK(6)>,]
     */
    object MeKeys: TlvTags("13")

    /**
     * МЭ Status
     */
    object MeStatus: TlvTags("14")

    /**
     * Tamper state string
     */
    object TamperState: TlvTags("15")

    /**
     * RKL:Signature. Присутствует если KLK передается через RKL
     */
    object RKLSignature: TlvTags("16")

    /**
     * RKL:Random. Присутствует если KLK передается через RKL.
     * Если отсутствует - будет взято значение, созданное предыдущей командой 0x0B
     */
    object RKLRandom: TlvTags("17")

    /**
     * Поле CN из сертификата RKL
     */
    object CNField: TlvTags("18")

    /**
     * RSA Random nonce (Присутствует для Offline Encrypted PIN)
     */
    object RSARandom: TlvTags("1A")

    /**
     * Сертификат устройства в DER виде
     */
    object DerCertificate: TlvTags("20")

    /**
     * МЭ Random 24 byte
     */
    object MeRandom: TlvTags("21")


    /**
     * Шифрованный на сертификате устройства 24 МЭ random + 24 Random библиотеки
     */
    object MeRandomEncrypted: TlvTags("22")

    /**
     * Зашифрованный Offline PIN block
     */
    object OfflinePin: TlvTags("2A")

    /**
     * New KLK under Old KLK
     */
    object NewKLK : TlvTags("40")

    /**
     * MPIN under KLK
     */
    object MPIN : TlvTags("41")

    /**
     * MMAC under KLK
     */
    object MMAC : TlvTags("42")

    /**
     * MTDK under KLK
     */
    object MTDK : TlvTags("43")

    /**
     * SPIN under MPIN
     */
    object SPIN : TlvTags("48")

    /**
     * SMAC under MMAC
     */
    object SMAC : TlvTags("49")

    /**
     * STDK under MTDK – не используется
     */
    object STDK: TlvTags("4A")

    /**
     * SENC under MMAC
     */
    object SENC: TlvTags("4B")

    /**
     * Track2 – если обнаружена магнитная карта
     */
    object Track2: TlvTags("52")

    /**
     * Track1 – если обнаружена магнитная карта
     */
    object Track1: TlvTags("56")

    /**
     * Full PAN
     */
    object PAN: TlvTags("5A")

    /**
     * Amount
     */
    object Amount: TlvTags("9F02")

    /**
     * Amount 2
     */
    object Amount2: TlvTags("9F03")

    /**
     * Full ICC public key (module) (Присутствует для Offline Encrypted PIN)
     */
    object PublicKey: TlvTags("9F2D")

    /**
     * ICC Exponent (Присутствует для Offline Encrypted PIN)
     */
    object ICCExponent: TlvTags("9F2E")
}