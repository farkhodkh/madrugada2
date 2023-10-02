package ru.petrolplus.pos.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.petrolplus.pos.room.BuildConfig
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

@Database(
    entities = [
        BaseSettingsDB::class,
        CommonSettingsDB::class,
        ShiftParamsDB::class,
        GUIDParamsDB::class,
        ServiceDB::class,
        TransactionDB::class,
        ReceiptParamsDB::class,
    ],
    version = 1,
    exportSchema = false,
)
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
            return if (BuildConfig.DEBUG) {
                Room.databaseBuilder(context, AppDatabase::class.java, "pos-test-database")
                    .addCallback(
                        callback = object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                with(db) {
                                    execSQL("INSERT INTO base_settings (id, acquirer_id, terminal_id, host_port, host_ip) VALUES(1, 2005, 26, '99.100.100.1', 4023)")
                                    execSQL("INSERT INTO common_settings (id, organization_name, organization_inn, pos_name) VALUES(1, 'Первая автокомпания', '111222333444', 'АЗС №1')")
                                    execSQL("INSERT INTO guid_params (id, last_online_transaction, last_generated_time, clock_sequence, has_node_id, node_id) VALUES (1, 57073, 1669910413, 25131, 1, 'CDE471DBA267')")
                                    execSQL("INSERT INTO shift_params (id, current_shift_number, current_shift_starts_timestamp) VALUES(1, 12, '2023-12-20 12:12:12')")
                                    execSQL("INSERT INTO receipt_params (id, current_receipt_number) VALUES(1, 12)")
                                    execSQL("INSERT INTO services (id, name, unit, price) VALUES(1, 'Аи-92', 'Л', 45000), (2, 'Аи-95', 'Л', 50000), (3, 'Аи-98', 'Л', 55000)")
                                }
                            }
                        },
                    )
                    .build()
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
            }
        }
    }
}
