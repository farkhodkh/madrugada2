package ru.petroplus.pos.p7LibApi.dto

/**
 * Класс простого строкового документа для печати
 * @property header - хэдер документа
 * @property title - заголовок документа
 * @property body - тело документа
 * @property footer - футер документа
 */

class PrintableDataDto (
    var header : String = String(),
    var title : String = String(),
    var body : String = String(),
    var footer : String = String()
)