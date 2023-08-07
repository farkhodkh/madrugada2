package ru.petroplus.pos.networkapi

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import ru.petroplus.pos.network.auth.AuthenticationParameters
import ru.petroplus.pos.network.ssl.NoSSLv3SocketFactory
import ru.petroplus.pos.network.ssl.SSLContextFactory
import ru.petroplus.pos.network.ssl.TrustAllX509TrustManager
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import kotlin.properties.Delegates

class GatewayServerImpl : IGatewayServerApi {
    private lateinit var authParams: AuthenticationParameters
    override lateinit var sslContext: SSLContext

    override var lastResponseCode by Delegates.notNull<Int>()
    private val trustAllCerts = arrayOf(TrustAllX509TrustManager())

    companion object {
        private var instance: GatewayServerImpl? = null

        fun getInstance(): GatewayServerImpl {
            if (instance == null) {
                val authParams = AuthenticationParameters()
                instance = GatewayServerImpl().apply {
                    this.authParams = authParams
                }
            }
            return instance!!
        }
    }

    init {
//        val clientCertFile = authParams.clientCertificate
//        sslContext = SSLContextFactory
//            .getFactoryInstance()
//            .makeContext(
//                clientCertFile,
//                authParams.clientCertificatePassword,
//                authParams.caCertificate
//            )
//
//        lastResponseCode = 0

        CookieHandler.setDefault(CookieManager())
    }

    override fun doPing() {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(OkHttpProfilerInterceptor())
        clientBuilder.callTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.connectTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.writeTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.sslSocketFactory(NoSSLv3SocketFactory(sslContext.socketFactory), trustAllCerts[0])

        val client = clientBuilder.build()
    }
}