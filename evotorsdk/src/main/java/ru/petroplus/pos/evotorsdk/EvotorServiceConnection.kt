package ru.petroplus.pos.evotorsdk

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import ru.evotor.pinpaddriver.external.api.ExternalLowLevelApiInterface

/**
 * Reserved for future use
 */
class EvotorServiceConnection : ServiceConnection {
    var apiInterface: ExternalLowLevelApiInterface? = null

    override fun onServiceConnected(className: ComponentName, service: IBinder) {
        apiInterface = ExternalLowLevelApiInterface.Stub.asInterface(service)
    }

    override fun onServiceDisconnected(className: ComponentName) {
        Log.e("TAG", "Service has unexpectedly disconnected")
        apiInterface = null
    }
}