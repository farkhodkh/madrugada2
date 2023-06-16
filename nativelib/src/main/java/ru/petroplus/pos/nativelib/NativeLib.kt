package ru.petroplus.pos.nativelib;

import android.util.Log
import java.util.*

//--------------------------------------------------------------------------------------------------
////Является полным аналогом TBackclallTypes c++
//enum class TP7LibCallback(val value: Int) {
//                                           NotGiven(0),
//                                           Log(1),
//                                           VoidInt(2);
//    companion object {
//        fun getByValue(value: Int) = TP7LibCallback.values().firstOrNull { it.value == value }
//    }
//}
//--------------------------------------------------------------------------------------------------

object NativeLibCallBacks {
    val nativeLib = NativeLib()

    fun log(Msg: String) {
        Log.d("MainActivity", "Log msg: $Msg")
        //NativeLib.getInstance().logObservable.onNext(Msg)
    }

    fun onNativeVoidCall(intValue: Int) {
        Log.d("MainActivity", "onNativeVoidCall result: $intValue")

        val newMessage = nativeLib.getNewMsg()
        //NativeLib.getInstance().messageObservable.onNext(newMessage)
    }

    fun onNativeCallReturn(someType: TSomeTypeJava): Int  {
        NativeLib.getInstance().updateSomeType(someType)
        return 5
    }


//    fun getMethodsMap(): Map<TP7LibCallback, Pair<String, String>> = mapOf(
//        TP7LibCallback.Log     to Pair("log",              "(Ljava/lang/String;)V"),
//        TP7LibCallback.VoidInt to Pair("onNativeVoidCall", "(I)V")
//    )
}
//--------------------------------------------------------------------------------------------------

class NativeLib {

//    val logObservable: PublishSubject<String> = PublishSubject.create()
//    val messageObservable: PublishSubject<ByteArray> = PublishSubject.create()

    /**
     * A native method that is implemented by the 'nativelib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    /**
     * Require version
     */
    external fun getDLLVersion(): String

    external fun init(callbackObj: NativeLibCallBacks)

    /**
     * Call send message
     */
    external fun sendMsg(byteArray: ByteArray): Boolean

    /**
     * Call IsNewMsg message
     */
    external fun IsNewMsg(): Int

    /**
     * Call send message
     */
    external fun getNewMsg(): ByteArray

    /**
     * Send ini file content
     */
    external fun sendIniProperties(properties: Properties)

    external fun createFile(FileName: String, Data: String) : Boolean
    external fun readFile(FileName: String, Data: TJavaMsg) : Boolean

    external fun sendJniString(value: String)

    //Get some cpp type object
    external fun getSomeTypeValue(): TSomeTypeJava //Any

    external fun sendSomeTypeValue(someType: TSomeTypeJava)
    fun updateSomeType(someType: TSomeTypeJava) {
        someType.strValue = ""
        someType.intValue = 100
        someType.doubleValue = 100.0
        someType.charValue = '0'
        someType.isBoolValue = !someType.isBoolValue
    }

    companion object {
        private var instance: NativeLib? = null

        fun getInstance(): NativeLib {
            if (instance == null) {
                instance = NativeLib()
            }

            return instance!!
        }

        // Used to load the 'nativelib' library on application startup.
        init {
            System.loadLibrary("nativelib")
        }
    }
}


