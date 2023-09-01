package ru.petrolplus.pos.p7LibApi.dto.card

/**
 * class для определения типов карт
 *  задаёт типы обслуживаемых карт
 */

sealed class CardType(val code: Int)

/**
 * Не известная карта
 */
object UnknownCardType : CardType(0)

/**
 * Карта типа A
 */
object P7_A : CardType(1)


/**
 * Карта типа B
 */
object P7_B : CardType(2)

/**
 * Карта типа C
 */
object P7_C : CardType(3)

/**
 * Карта типа D
 */
object P7_D : CardType(4)

/**
 * Карта типа E
 */
object P7_E : CardType(5)

/**
 * Карта типа F
 */
object P7_F : CardType(6)

/**
 * Карта типа G
 */
object P7_G : CardType(7)

/**
 * Карта типа H
 */
object P7_H : CardType(8)

/**
 * Карта типа I
 */
object P7_I : CardType(9)

/**
 * Карта типа J
 */
object P7_J : CardType(10)

/**
 * Карта типа Petrol 5
 */
object P5 : CardType(11)

/**
 * Карта типа Petrol Pay
 */
object PPay : CardType(12)

/**
 * Карта типа LNR
 */
object PLNR : CardType(13)

/**
 * Карта внешней системы Likard
 */
object ExtLikard : CardType(14)

/**
 * Карта внешней системы РосНефть
 */
object ExtRN : CardType(15)

/**
 * Карта внешней системы GPN
 */
object ExtGPN : CardType(16)