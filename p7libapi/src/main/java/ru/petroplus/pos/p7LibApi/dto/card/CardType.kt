package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Interface для определения типов карт
 *  TODO - Заполнить описание всех реализаций
 */
sealed interface CardType

/**
 * Не известная карта
 */
object UnknownCardType : CardType

/**
 *
 */
object P7_A : CardType


/**
 *
 */
object P7_B : CardType

/**
 *
 */
object P7_C : CardType

/**
 *
 */
object P7_D : CardType

/**
 *
 */
object P7_E : CardType

/**
 *
 */
object P7_F : CardType

/**
 *
 */
object P7_G : CardType

/**
 *
 */
object P7_H : CardType

/**
 *
 */
object P7_I : CardType

/**
 *
 */
object P7_J : CardType

/**
 *
 */
object P5 : CardType

/**
 *
 */
object PPay : CardType

/**
 *
 */
object PLNR : CardType

/**
 *
 */
object ExtLikard : CardType

/**
 *
 */
object ExtRn : CardType

/**
 *
 */
object ExtGpn : CardType