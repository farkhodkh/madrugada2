package ru.petrolplus.pos

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.TransactionDB
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class ReceiptProjectionTest {
    
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    private val transactionsDao = db.transactionDao()
    private val commonSettingsDao = db.commonSettingsDao()
    private val receiptParamsDao = db.receiptParamsDao()
    private val servicesDao = db.servicesDao()
    private val receiptDao = db.receiptDao()

    
    @Test
    fun testGetDebitReceipt() {
        runBlocking {
            transactionsDao.insert(TransactionDB(
                id = "ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD",
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
                operationType = 2,
                cardType = 2,
                loyaltySum = 2147483647000,
                deltaBonus = 2147483647000,
                originalDebitTransactionId = "",
                shiftNumber = 30,
                hasRecalculationTransaction = false,
                rollbackCode = "IELSBMDSOOKKHUEL",
                receiptNumber = 2147483647000,
                responseCode = 19
            ))

            commonSettingsDao.insert(CommonSettingsDB(1, "Первая автокомпания", "111222333444", "АЗС №1"))
            receiptParamsDao.insert(ReceiptParamsDB(1, 3))
            servicesDao.insert(ServiceDB(1, "АИ-95", "Л", 45000))

            val receipt = receiptDao.getDebitReceiptByTransactionId("ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD")
            Assert.assertEquals(receipt?.transactionDB?.id, "ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD")
        }

    }
    
    
}