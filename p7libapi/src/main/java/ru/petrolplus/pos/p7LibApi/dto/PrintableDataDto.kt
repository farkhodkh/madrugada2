package ru.petrolplus.pos.p7LibApi.dto

/**
 * Класс простого строкового документа для печати
 * @property header - хэдер документа
 * @property title - заголовок документа
 * @property body - тело документа
 * @property footer - футер документа
 */

//todo: при возможности, использовать val и убрать инициализацию (требует существенной переработки JNI)
class PrintableDataDto (
    var header : String,
    var title : String,
    var body : String,
    var footer : String
)