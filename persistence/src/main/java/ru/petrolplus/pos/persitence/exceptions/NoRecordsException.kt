package ru.petrolplus.pos.persitence.exceptions

//TODO перести текст в строковые ресурсы после слияния с веткое где есть утилиты для получения строк без прямого доступа к context
object NoRecordsException : IllegalStateException("Запрашиваемых настроек не существует в хранилище")