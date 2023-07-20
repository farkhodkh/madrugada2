package ru.petroplus.pos.p7LibApi.dto.card

/**
 * Interface для определения типов карт
 *  TODO -  Юрий добавь описание
 */



//sealed class ResultCode(val code: Int)
sealed class CardType(val code: Int)

/**
 * Не известная карта
 */
object UnknownCardType : CardType(0)

/**
 *
 */
object P7_A : CardType(1)


/**
 *
 */
object P7_B : CardType(2)

/**
 *
 */
object P7_C : CardType(3)

/**
 *
 */
object P7_D : CardType(4)

/**
 *
 */
object P7_E : CardType(5)

/**
 *
 */
object P7_F : CardType(6)

/**
 *
 */
object P7_G : CardType(7)

/**
 *
 */
object P7_H : CardType(8)

/**
 *
 */
object P7_I : CardType(9)

/**
 *
 */
object P7_J : CardType(10)

/**
 *
 */
object P5 : CardType(11)

/**
 *
 */
object PPay : CardType(12)

/**
 *
 */
object PLNR : CardType(13)

/**
 *
 */
object ExtLikard : CardType(14)

/**
 *
 */
object ExtRN : CardType(15)

/**
 *
 */
object ExtGPN : CardType(16)