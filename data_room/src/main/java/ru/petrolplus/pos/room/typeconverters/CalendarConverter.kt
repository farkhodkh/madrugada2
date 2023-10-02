package ru.petrolplus.pos.room.typeconverters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 *
 * Конвертирует дату и время в формате Calendar в число по шаблону указаному в DATE_FORMAT и обратно
 */
class CalendarConverter {

    private val formatter = SimpleDateFormat(DATE_FORMAT, RU)

    /**
     * Конвертирует эксземпляр календаря в строковое представление
     * @param calendar календарь Java
     * @return штамп времени в строковом представлении
     */
    @TypeConverter
    fun toString(calendar: Calendar): String {
        return formatter.format(calendar.time)
    }

    /**
     * Конвертирует строку в эксземпляр календаря Java, посредством парсинга
     * нуллабельность можно проигнорировать т.к парситься будут заранее известные строки сохраненные этим
     * же конвертером, так что
     * @param timestamp штамп времени в строковом представлении
     * @return экземпляр календаря со значение времени считанного из строки
     */
    @TypeConverter
    fun fromString(timestamp: String): Calendar {
        return Calendar.getInstance(RU).also {
            it.time = formatter.parse(timestamp)!!
        }
    }
}

private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
private val RU = Locale("ru")
