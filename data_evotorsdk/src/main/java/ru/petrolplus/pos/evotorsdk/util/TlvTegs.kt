package ru.petroplus.pos.evotorsdk.util

/**
 * Класс для перечисления тегов для команд TLV
 * Описание тегов из оригинального описания API
 */
sealed class TlvTegs(val code: String) {
    /**
     * Card found: 0:None, 1:MS, 2:ICC, 4:CLess
     */
    object CardFound: TlvTegs("01")

    /**
     * ATR if present
     */
    object Atr: TlvTegs("02")

    /**
     * CLA INS P1 P2 Lc <DataCmd> Le – для ICC и NFC интерфейсов
     */
    object Cla: TlvTegs("03")

    /**
     * DataAnswer
     */
    object DataAnswer: TlvTegs("04")

    /**
     * SW от карты
     */
    object SW: TlvTegs("05")

    /**
     * PinMode: 1:OnLine, 2:OfflinePlaintext, 3:OfflineEnc
     */
    object PinMode: TlvTegs("06")

    /**
     * Random. 8 бинарных байт случайного числа,  созданных СМ.
     */
    object Random: TlvTegs("07")

    /**
     * OnLine PIN block
     */
    object OnLinePin: TlvTegs("0A")

    /**
     * Check общего ключа
     */
    object CheckKey: TlvTegs("0В")

    /**
     * Delete key (длина тэга = 0)
     * Plaintext KLK
     * RKL:RSA encrypted KLK. Размер тэга 256 является признаком использования RKL
     */
    object DeleteKey: TlvTegs("0C")

    /**
     * Check общего ключа. Если отсутствует – ключ не согласован
     */
    object KLKKey: TlvTegs("0D")

    /**
     * Data for encrypt
     */
    object DataForEncrypt: TlvTegs("0E")

    /**
     * Data encrypted 3DES ECB
     */
    object DataEncrypted: TlvTegs("0F")

    /**
     * МЭ Serial
     */
    object MeSerial: TlvTegs("10")

    /**
     * МЭ Model
     */
    object MeModel: TlvTegs("11")

    /**
     * МЭ Version
     */
    object MeVersion: TlvTegs("12")

    /**
     * МЭ Keys check list [<Name(4)>:<CHK(6)>,]
     */
    object MeKeys: TlvTegs("13")

    /**
     * МЭ Status
     */
    object MeStatus: TlvTegs("14")

    /**
     * Tamper state string
     */
    object TamperState: TlvTegs("15")

    /**
     * RKL:Signature. Присутствует если KLK передается через RKL
     */
    object RKLSignature: TlvTegs("16")

    /**
     * RKL:Random. Присутствует если KLK передается через RKL.
     * Если отсутствует - будет взято значение, созданное предыдущей командой 0x0B
     */
    object RKLRandom: TlvTegs("17")

    /**
     * Поле CN из сертификата RKL
     */
    object CNField: TlvTegs("18")

    /**
     * RSA Random nonce (Присутствует для Offline Encrypted PIN)
     */
    object RSARandom: TlvTegs("1A")

    /**
     * Сертификат устройства в DER виде
     */
    object DerCertificate: TlvTegs("20")

    /**
     * МЭ Random 24 byte
     */
    object MeRandom: TlvTegs("21")


    /**
     * Шифрованный на сертификате устройства 24 МЭ random + 24 Random библиотеки
     */
    object MeRandomEncrypted: TlvTegs("22")

    /**
     * Зашифрованный Offline PIN block
     */
    object OfflinePin: TlvTegs("2A")

    /**
     * New KLK under Old KLK
     */
    object NewKLK : TlvTegs("40")

    /**
     * MPIN under KLK
     */
    object MPIN : TlvTegs("41")

    /**
     * MMAC under KLK
     */
    object MMAC : TlvTegs("42")

    /**
     * MTDK under KLK
     */
    object MTDK : TlvTegs("43")

    /**
     * SPIN under MPIN
     */
    object SPIN : TlvTegs("48")

    /**
     * SMAC under MMAC
     */
    object SMAC : TlvTegs("49")

    /**
     * STDK under MTDK – не используется
     */
    object STDK: TlvTegs("4A")

    /**
     * SENC under MMAC
     */
    object SENC: TlvTegs("4B")

    /**
     * Track2 – если обнаружена магнитная карта
     */
    object Track2: TlvTegs("52")

    /**
     * Track1 – если обнаружена магнитная карта
     */
    object Track1: TlvTegs("56")

    /**
     * Full PAN
     */
    object PAN: TlvTegs("5A")

    /**
     * Amount
     */
    object Amount: TlvTegs("9F02")

    /**
     * Amount 2
     */
    object Amount2: TlvTegs("9F03")

    /**
     * Full ICC public key (module) (Присутствует для Offline Encrypted PIN)
     */
    object PublicKey: TlvTegs("9F2D")

    /**
     * ICC Exponent (Присутствует для Offline Encrypted PIN)
     */
    object ICCExponent: TlvTegs("9F2E")
}