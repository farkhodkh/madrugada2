package ru.petroplus.pos.network.repository

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import ru.petroplus.pos.network.auth.AuthenticationParameters
import ru.petroplus.pos.network.auth.GatewayAuthenticationUtil
import ru.petroplus.pos.network.ssl.NoSSLv3SocketFactory
import ru.petroplus.pos.network.ssl.SSLContextFactory
import ru.petroplus.pos.network.ssl.TrustAllX509TrustManager
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.networkapi.request.PingRequest
import ru.petroplus.pos.util.constants.Constants.GATEWAY_SERVER_ADDRESS_AND_PORT
import java.net.CookieHandler
import java.net.CookieManager
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import kotlin.properties.Delegates

class GatewayServerRepositoryImpl: GatewayServerRepositoryApi {
    private var authParams: AuthenticationParameters
    private val trustAllCerts = arrayOf(TrustAllX509TrustManager())

    override lateinit var sslContext: SSLContext

    override var lastResponseCode by Delegates.notNull<Int>()

    companion object {
        private var instance: GatewayServerRepositoryImpl? = null

        fun getInstance(): GatewayServerRepositoryImpl {
            if (instance == null) {
                instance = GatewayServerRepositoryImpl().apply {
                    this.authParams = authParams
                //GatewayAuthenticationUtil.gateGatewayAuthenticationParams()
                }
            }
            return instance!!
        }
    }

    init {
        authParams = GatewayAuthenticationUtil
            .gateGatewayAuthenticationParams()

        val clientCertFile = authParams.clientCertificate
        sslContext = SSLContextFactory
            .getFactoryInstance()
            .makeContext(
                clientCertFile,
                authParams.clientCertificatePassword,
                authParams.caCertificate
            )
        CookieHandler.setDefault(CookieManager())
    }

    override fun doPing() {
        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.hostnameVerifier { hostname, _ ->
            GATEWAY_SERVER_ADDRESS_AND_PORT.startsWith(
                "https://$hostname",
                true
            )
        }

        clientBuilder.addInterceptor(OkHttpProfilerInterceptor())
        clientBuilder.callTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.connectTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.writeTimeout(3000L, TimeUnit.MILLISECONDS)
        clientBuilder.sslSocketFactory(NoSSLv3SocketFactory(sslContext.socketFactory), trustAllCerts[0])

        val body = GatewayAuthenticationUtil
            .getPingBinFile()
            .asRequestBody()

        val client = clientBuilder.build()

        val request = Request.Builder()
            .url(GATEWAY_SERVER_ADDRESS_AND_PORT)
            .post(body)
            .addHeader("SECURITY_FLAGS", "SECURITY_FLAG_IGNORE_UNKNOWN_CA, SECURITY_FLAG_IGNORE_CERT_WRONG_USAGE, SECURITY_FLAG_IGNORE_CERT_CN_INVALID, SECURITY_FLAG_IGNORE_CERT_DATE_INVALID")
            .addHeader("Accept", "application/octet-stream")
            .addHeader("Connection", "Keep-Alive")
            .addHeader("User-Agent", "P7_client")
            .addHeader("X-SSL-Client-CN", "POS-4000-00001-123456789")
            .addHeader("X-Terminal-IP", "127.0.0.1")
            .addHeader("Content-Type", "application/octet-stream")
            .build()

        try {
//            val retrofitBuilder = Retrofit
//                .Builder()
//            retrofitBuilder.client(client)
//
//            val f = retrofitBuilder.build()

            val response = client.newCall(request).execute();
            lastResponseCode = response.code
            val result = response.code
            val responseBody = response.body
        } catch (ex: Exception) {
            val b = ex.message
            val f = 0
        }
    }

    suspend fun pingGatewayServer(request: PingRequest) {
        TODO("Not yet implemented")
    }
}