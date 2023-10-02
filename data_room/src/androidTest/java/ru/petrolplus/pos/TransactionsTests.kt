package ru.petrolplus.pos

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petrolplus.pos.room.entities.TransactionDB
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class TransactionsTests {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    private val transactionsDao = db.transactionDao()

    @After
    fun reset() {
        db.clearAllTables()
    }

    @Test
    fun testGetLastTransactionByCardNumberAndServiceId() {
        runBlocking {
            transactionsDao.insert(
                TransactionDB(
                    id = "ABCDEFGIDSLKJDIFELSKJKJKDSFOOBTB",
                    acquirerId = 1005,
                    terminalId = 2,
                    cardNumber = "1005000000",
                    operatorNumber = "4000123456",
                    terminalDate = Calendar.getInstance(),
                    serviceIdWhat = 1,
                    serviceIdFrom = 3,
                    amount = 2147483647000,
                    price = 2147483647000,
                    sum = 2147483647000,
                    cardTransactionCounter = 2147483647000,
                    hasReturn = false,
                    crs32 = "DJIFESLD",
                    operationType = 1,
                    cardType = 2,
                    loyaltySum = 2147483647000,
                    deltaBonus = 2147483647000,
                    originalDebitTransactionId = "",
                    shiftNumber = 30,
                    hasRecalculationTransaction = false,
                    rollbackCode = "IELSBMDSOOKKHUEL",
                    receiptNumber = 2147483647000,
                    responseCode = 19,
                ),
            )

            delay(2000)

            transactionsDao.insert(
                TransactionDB(
                    id = "ABCDEFGIDSLKJDIFELSKJKJKDSFBBDDD",
                    acquirerId = 1005,
                    terminalId = 2,
                    cardNumber = "1005000000",
                    operatorNumber = "4000123456",
                    terminalDate = Calendar.getInstance(),
                    serviceIdWhat = 1,
                    serviceIdFrom = 3,
                    amount = 2147483647000,
                    price = 2147483647000,
                    sum = 2147483647000,
                    cardTransactionCounter = 2147483647000,
                    hasReturn = false,
                    crs32 = "DJIFESLD",
                    operationType = 1,
                    cardType = 2,
                    loyaltySum = 2147483647000,
                    deltaBonus = 2147483647000,
                    originalDebitTransactionId = "",
                    shiftNumber = 30,
                    hasRecalculationTransaction = false,
                    rollbackCode = "IELSBMDSOOKKHUEL",
                    receiptNumber = 2147483647000,
                    responseCode = 19,
                ),
            )

            val transactionDB = transactionsDao.getLastByCardNumberAndService("1005000000", 1)
            Assert.assertEquals(transactionDB?.id, "ABCDEFGIDSLKJDIFELSKJKJKDSFBBDDD")
        }
    }
}
