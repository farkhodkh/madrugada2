package ru.petrolplus.pos.evotorprinter

import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.petrolplus.pos.evotorprinter.GeneralComponents.textJustify
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.PRICE_UNIT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.SERVICE
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.SERVICE_AMOUNT
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.SERVICE_PRICE
import ru.petrolplus.pos.printerapi.IntroductoryConstruction.SERVICE_SUM
import ru.petrolplus.pos.printerapi.ext.toAmountString
import ru.petrolplus.pos.printerapi.ext.toCurrencyString

/**
 * Объект который отвечает за формирование сущностей для печати данных о сервисе/-ах
 */
object ServiceComponents {

    /**
     * Функция для формирования данных о сервисе в виде "таблицы"
     * @param serviceName название сервиса
     * @param serviceUnit единицы исч. сервиса
     * @param servicePrice цена за единицу сервиса
     * @param sum сумма на которую была проведена операция с сервисом
     * @param amount кол-во участвующих в операции единиц сервиса
     * @param paperWidth ширина чека (чековой бумаги) в кол-ве символов
     * @return массив сущностей для печати
     */
    internal fun getServiceTable(
        serviceName: String,
        serviceUnit: String,
        servicePrice: Long,
        sum: Long,
        amount: Long,
        paperWidth: Int
    ): Array<IPrintable> = arrayOf(
        arrayOf(SERVICE, serviceName).textJustify(paperWidth),
        arrayOf(SERVICE_AMOUNT, serviceUnit, amount.toAmountString()).textJustify(paperWidth),
        arrayOf(SERVICE_PRICE, PRICE_UNIT, servicePrice.toCurrencyString()).textJustify(paperWidth),
        arrayOf(SERVICE_SUM, PRICE_UNIT, sum.toCurrencyString()).textJustify(paperWidth),
    )
}