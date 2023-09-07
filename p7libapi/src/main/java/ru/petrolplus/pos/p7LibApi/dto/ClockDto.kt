package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс представления времени
 * @property year - год, отсчитывается от 1900 г.
 * @property month - месяц, 1=JAN ...  12=DEC
 * @property day - день месяца, 1 ... 28/29/30/31
 * @property hour - часы, 0 ... 23
 * @property minute - минуты, 0 ... 59
 * @property second - секунды, 0 ... 59
 * @property weekDay - день недели, 1=MON ... 7=SUN
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
//todo: возможно, название класса не оптимально, при возможности подумать над переименованием.
class ClockDto {
    var year : Short = 0
    var month : Short = 0
    var day : Short = 0
    var hour : Short = 0
    var minute : Short = 0
    var second : Short = 0
    var weekDay : Short = 0
}