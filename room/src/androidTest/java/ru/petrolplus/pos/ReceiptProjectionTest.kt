package ru.petrolplus.pos

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ReceiptParamsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class ReceiptProjectionTest {
    
    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    private val transactionsDao = db.transactionDao()
    private val commonSettingsDao = db.commonSettingsDao()
    private val receiptParamsDao = db.receiptParamsDao()
    private val shiftParamsDao = db.shiftParamsDao()
    private val servicesDao = db.servicesDao()
    private val receiptDao = db.receiptDao()

    @After
    fun reset() {
        db.clearAllTables()
    }
    
    @Test
    fun testGetDebitReceipt() {
        runBlocking {
            val transactionId = "ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD"
            transactionsDao.insert(createTestTransaction(id = transactionId))

            commonSettingsDao.insert(CommonSettingsDB(1, "Первая автокомпания", "111222333444", "АЗС №1"))
            receiptParamsDao.insert(ReceiptParamsDB(1, 3))
            servicesDao.insert(ServiceDB(1, "АИ-95", "Л", 45000))

            val receipt = receiptDao.getDebitReceiptByTransactionId(transactionId)

            Assert.assertEquals(receipt?.transactionDB?.id, transactionId)
            Assert.assertEquals(receipt?.receiptParamsDB?.id, 1)
            Assert.assertEquals(receipt?.commonSettingsDB?.id, 1)
            Assert.assertEquals(receipt?.serviceDB?.id, 1)
        }
    }

    @Test
    fun testGetShiftReport() {
        runBlocking {
            servicesDao.insert(
                listOf(
                    ServiceDB(1, "АИ-92", "Л", 45000),
                    ServiceDB(2, "АИ-95", "Л", 55000),
                    ServiceDB(3, "АИ-98", "Л", 60000)
                )
            )

            transactionsDao.insert(
                listOf(
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD", 1, 1, 45000, 200000, true),
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJKJKDEIOQFYW", 1, 1, 55000, 200000),
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJFUKKJXHUUSI", 2, 1),
                    createTestTransaction("ABCDEJHDHSKHFJKHEHIKJKJKDSFDIJSD", 3, 1),
                    createTestTransaction("FLDJKSJNLHHDKJHSKJEHKJHEKJHLKJHH", 2, 4),
                    createTestTransaction("JLHJHFSLKHDJKHKJSHDKHFUEHHJKLHLH", 3, 4),
                    createTestTransaction("HJHKEHKLHSDHJHLKSHBUHUEHKHSHKHJH", 1, 1, 35000, 200000)
                )
            )

            val projection = receiptDao.getReportByOperationTypeForShift(1, 30)

            Log.d("projection", projection.toString())

            Assert.assertEquals(projection.size, 3)

        }
    }

    @Test
    fun testGetReceiptParamsWithCommonSettings() {
        runBlocking {
            shiftParamsDao.insert(ShiftParamsDB(1, 12, Calendar.getInstance()))
            commonSettingsDao.insert(CommonSettingsDB(1, "Первая автокомпания", "111222333444", "АЗС №1"))

            val projection = receiptDao.getShiftInfo()

            Assert.assertEquals(projection.shiftParamsDB.id, 1)
            Assert.assertEquals(projection.commonSettingsDB.id, 1)
        }
    }

    @Test
    fun testGetCardsTotal() {
        runBlocking {
            transactionsDao.insert(
                listOf(
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD", 1, 1, 45000, 200000, true),
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJKJKDEIOQFYW", 1, 1, 55000, 200000),
                    createTestTransaction("ABCDEFGIDSLKJDIFELSKJFUKKJXHUUSI", 2, 1, sum = 200000),
                    createTestTransaction("ABCDEJHDHSKHFJKHEHIKJKJKDSFDIJSD", 3, 1, sum = 200000),
                    createTestTransaction("FLDJKSJNLHHDKJHSKJEHKJHEKJHLKJHH", 2, 4, sum = 400000),
                    createTestTransaction("JLHJHFSLKHDJKHKJSHDKHFUEHHJKLHLH", 3, 4, sum = 400000),
                    createTestTransaction("HJHKEHKLHSDHJHLKSHBUHUEHKHSHKHJH", 1, 1, 35000, 200000)
                )
            )

            val projection = receiptDao.getCardsTotal(30)
            Log.d("projection", projection.toString())
            Assert.assertEquals(5, projection.totalDebits)
            Assert.assertEquals(2, projection.totalRefunds)
            Assert.assertEquals(7, projection.totalOperations)
            Assert.assertEquals(200000, projection.totalSum)
        }
    }
}

fun createTestTransaction(
    id: String = "ABCDEFGIDSLKJDIFELSKJKJKDSFDIJSD",
    serviceId: Int = 1,
    operationType: Int = 1,
    amount: Long = 2147483647000,
    sum: Long = 2147483647000,
    hasRecalcTransaction: Boolean = false,
    shiftNumber: Int = 30
): TransactionDB {
    return TransactionDB(
        id = id,
        acquirerId = 1005,
        terminalId = 2,
        cardNumber = "1005000000",
        operatorNumber = "4000123456",
        terminalDate = Calendar.getInstance(),
        serviceIdWhat = serviceId,
        serviceIdFrom = 3,
        amount = amount,
        price = 2147483647000,
        sum = sum,
        cardTransactionCounter = 2147483647000,
        hasReturn = false,
        crs32 = "DJIFESLD",
        operationType = operationType,
        cardType = 2,
        loyaltySum = 2147483647000,
        deltaBonus = 2147483647000,
        originalDebitTransactionId = "",
        shiftNumber = shiftNumber,
        hasRecalculationTransaction = hasRecalcTransaction,
        rollbackCode = "IELSBMDSOOKKHUEL",
        receiptNumber = 2147483647000,
        responseCode = 19
    )
}