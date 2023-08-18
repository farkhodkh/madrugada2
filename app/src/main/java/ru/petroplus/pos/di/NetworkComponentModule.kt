package ru.petroplus.pos.di

import android.content.Context
import androidx.work.DelegatingWorkerFactory
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.network.auth.GatewayAuthenticationUtil
import ru.petroplus.pos.network.repository.GatewayServerRepositoryImpl
import ru.petroplus.pos.network.ssl.NoSSLv3SocketFactory
import ru.petroplus.pos.network.ssl.SSLContextFactory
import ru.petroplus.pos.network.ssl.TrustAllX509TrustManager
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.networkworker.executor.GatewayExchangeExecutor
import ru.petroplus.pos.networkworker.executor.GatewayExchangeExecutorApi
import ru.petroplus.pos.networkworker.worker.GatewayConfigFactory
import ru.petroplus.pos.networkworker.worker.GatewayConfigScheduler
import ru.petroplus.pos.util.constants.Constants
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

@Module
object NetworkComponentModule {

    @[Provides AppScope]
    fun providesTrustAllCerts(): Array<TrustAllX509TrustManager> =
        arrayOf(TrustAllX509TrustManager())

    @[Provides AppScope]
    fun provideSSLContextFactory(
        trustAllCerts: Array<TrustAllX509TrustManager>
    ): SSLSocketFactory {
        val authParams = GatewayAuthenticationUtil.gateGatewayAuthenticationParams()

        return SSLContextFactory
            .getFactoryInstance()
            .makeContext(
                authParams.clientCertificate,
                authParams.clientCertificatePassword,
                authParams.caCertificate,
                trustAllCerts
            ).socketFactory
    }

    @[Provides AppScope]
    fun provideOkHttpClient(
        trustAllCerts: Array<TrustAllX509TrustManager>,
        socketFactory: SSLSocketFactory
    ): OkHttpClient {

        val clientBuilder = OkHttpClient
            .Builder()
            .retryOnConnectionFailure(false)
            .addInterceptor(OkHttpProfilerInterceptor())
            .callTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .writeTimeout(30L, TimeUnit.SECONDS)
            .sslSocketFactory(NoSSLv3SocketFactory(socketFactory), trustAllCerts[0])
            .hostnameVerifier { hostname, _ ->
                Constants.GATEWAY_SERVER_ADDRESS_AND_PORT.startsWith(
                    "https://$hostname",
                    true
                )
            }

        return clientBuilder.build()
    }

    @[Provides AppScope]
    internal fun provideRetrofitBuilder(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.GATEWAY_SERVER_ADDRESS_AND_PORT)
            .client(client)
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .serializeNulls()
                            .setLenient()
                            .create()
                    )
            )
            .build()
    }

    @[Provides AppScope]
    fun provideGatewayServer(builder: Retrofit): GatewayServerApi =
        builder.create(GatewayServerApi::class.java)

    @[Provides AppScope]
    fun providesGatewayServerRepositoryApi(gatewayServer: GatewayServerApi): GatewayServerRepositoryApi =
        GatewayServerRepositoryImpl(gatewayServer)

    @[Provides AppScope]
    fun providesGatewayExchangeExecutor(context: Context): GatewayExchangeExecutorApi =
        GatewayExchangeExecutor(context)

    @[Provides AppScope]
    fun providesGatewayConfigScheduler(): GatewayConfigScheduler = GatewayConfigScheduler()

    @[Provides AppScope]
    fun providesGatewayConfigFactory(gatewayExchangeExecutor: GatewayExchangeExecutorApi): GatewayConfigFactory =
        GatewayConfigFactory(gatewayExchangeExecutor)

    @[Provides AppScope]
    fun providesDelegatingWorkerFactory(remoteConfigFactory: GatewayConfigFactory): DelegatingWorkerFactory =
        DelegatingWorkerFactory().apply {
            addFactory(remoteConfigFactory)
        }
}