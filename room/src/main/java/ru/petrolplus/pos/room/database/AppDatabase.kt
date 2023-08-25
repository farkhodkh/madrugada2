package ru.petrolplus.pos.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.petrolplus.pos.room.dao.BaseSettingsDao
import ru.petrolplus.pos.room.dao.CommonSettingsDao
import ru.petrolplus.pos.room.dao.GUIDParamsDao
import ru.petrolplus.pos.room.dao.ReceiptDao
import ru.petrolplus.pos.room.dao.ReceiptParamsDao
import ru.petrolplus.pos.room.dao.ServicesDao
import ru.petrolplus.pos.room.dao.ShiftParamsDao
import ru.petrolplus.pos.room.dao.TransactionsDao
import ru.petrolplus.pos.room.entities.BaseSettingsDB
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.GUIDParamsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB
import ru.petrolplus.pos.room.typeconverters.CalendarConverter

@Database(entities = [
    BaseSettingsDB::class,
    CommonSettingsDB::class,
    ShiftParamsDB::class,
    GUIDParamsDB::class,
    ServiceDB::class,
    TransactionDB::class,
    ReceiptParamsDB::class
], version = 1, exportSchema = false)
@TypeConverters(CalendarConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionsDao
    abstract fun servicesDao(): ServicesDao
    abstract fun baseSettingsDao(): BaseSettingsDao
    abstract fun commonSettingsDao(): CommonSettingsDao
    abstract fun guidParamsDao(): GUIDParamsDao
    abstract fun shiftParamsDao(): ShiftParamsDao
    abstract fun receiptParamsDao(): ReceiptParamsDao
    abstract fun receiptDao(): ReceiptDao

    companion object {

        private const val DATABASE_NAME = "pos-database"
        fun getInstance(context: Context): AppDatabase {
           return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}