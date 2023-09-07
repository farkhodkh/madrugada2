package ru.petrolplus.pos

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.petrolplus.pos.persitence.ReceiptPersistenceImpl
import ru.petrolplus.pos.persitence.mappers.ReceiptMapper
import ru.petrolplus.pos.persitence.mappers.ShiftReceiptMapper
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petrolplus.pos.room.entities.CommonSettingsDB
import ru.petrolplus.pos.room.entities.ServiceDB
import ru.petrolplus.pos.room.entities.ShiftParamsDB
import ru.petrolplus.pos.room.entities.TransactionDB
import ru.petrolplus.pos.util.constants.TestConstants
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class ReceiptPersistenceTest {

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
    private val receiptDao = db.receiptDao()
    private val receiptPersistence = ReceiptPersistenceImpl(receiptDao, ReceiptMapper(), ShiftReceiptMapper())

    private val transactionsDao = db.transactionDao()
    private val commonSettingsDao = db.commonSettingsDao()
    private val shiftParamsDao = db.shiftParamsDao()
    private val servicesDao = db.servicesDao()

    @Test
    fun testGetShiftReceipt() {
        runBlocking {
            shiftParamsDao.insert(ShiftParamsDB(1, 11, Calendar.getInstance()))
            commonSettingsDao.insert(CommonSettingsDB(1, TestConstants.ORGANIZATION_NAME, TestConstants.ORGANIZATION_INN, TestConstants.ORGANIZATION_POS))
            servicesDao.insert(
                listOf(
                    ServiceDB(1, TestConstants.SERVICE_AI_92, TestConstants.LITRES, 45000),
                    ServiceDB(2, TestConstants.SERVICE_AI_95, TestConstants.LITRES, 50000),
                    ServiceDB(3, TestConstants.SERVICE_AI_98, TestConstants.LITRES, 55000),
                )
            )
            transactionsDao.insert(
                listOf(
                    createTestTransaction("3ZTSOHXMUEAG5YKQ2N1CDLIFWJ6V7RB8", 1, 4, 78344, 45000, true, 11),
                    createTestTransaction("9PWG72C4XIRUN3JAVM5ETQ8HKLSYDZFO", 2, 1, 45209, 55000, true, 11),
                    createTestTransaction("L4BY5DVJFZ8KPU2CR7IN6TGX9QMEHSAW", 1, 5, 63578,  200000, false, 11),
                    createTestTransaction("KZHEUQPLX8BY0A53FGDM1VO7I2NC4T6R", 2, 5, 87905,  200000, true, 11),
                    createTestTransaction("X7GRVPH8ACIOK2Z6L9F1BQDNWJM4U3ES", 3, 1, 21476,  400000, false, 11),
                    createTestTransaction("SQV63R4JWZEOHLMY2XIP8AFKGNCD5TBV", 3, 4, 39627,  400000, false, 11),
                    createTestTransaction("B7QKZN9X5VIOMR4PULY1JACG2W6E3D8S", 2, 5, 51894, 200000, true, 11),
                    createTestTransaction("FYVC6ONERX3IQWAZU9TLG5H0BK7P4D2J", 1, 1, 67041, 45000 , true, 11),
                    createTestTransaction("HSKWYAM4C2J8N9QXL5RDGVI3O0FBP7TE", 2, 4, 14892, 55000 , false, 11),
                    createTestTransaction("7K45BNZWIR3XP2EO6FYVUD9LGA1JQH8M", 3, 1, 92473, 200000, false, 11),
                    createTestTransaction("3LQ4NZK2FXRVCWJIEGSDM0TOU5B6APY8", 1, 5, 30965, 200000, true, 11),
                    createTestTransaction("7G1O9ES2PLWBRHT6IXZ5KQAMFJY8VCDN", 3, 4, 76103, 400000, false, 11),
                    createTestTransaction("8L7UTVOZKPQ4ANXDGH6RJW5I3CM2SEYF", 2, 5, 58726, 400000, true, 11),
                    createTestTransaction("R6ZKGA0NS7B8D9OT2YMEQHIF5LPCXJWD", 2, 4, 43571, 35000, true, 11),
                    createTestTransaction("3F1YKIXJW9BQ8OGU5AVHZ6DPLM4N7ESR", 1, 5, 12009, 45000, false, 11),
                    createTestTransaction("DVZEHUG3PKS01QB7C5JX2YAMRLIN8OFW", 3, 1, 89234, 55000, false, 11),
                    createTestTransaction("9N7AQOIRFKX5BZ1JWPG3YU8L2M0SHVEC", 1, 1, 67384, 200000, true, 11),
                    createTestTransaction("7E4M0CFZT5PH8KI1GN6JD2YORL3BUWAV", 2, 4, 54536, 200000, true, 11),
                    createTestTransaction("O5JACB6HPXKRMISQD2GTWV4Z8F3LU7E9", 3, 5, 48250, 400000, true, 11),
                    createTestTransaction("YUIQWKJLOX5TPRBG87ZNSD34F12M69HV", 3, 4, 96417, 400000, false, 11),
                )
            )

            val receipt = receiptPersistence.getShiftReceipt()

            Log.d("receipt", receipt.toString())

            Assert.assertTrue(receipt.debits.size == 3 && (receipt.totalDebitsOperations + receipt.totalRefundsOperations) == receipt.totalOperations)
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