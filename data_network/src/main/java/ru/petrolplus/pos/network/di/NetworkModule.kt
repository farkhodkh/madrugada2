package ru.petrolplus.pos.network.di

import android.util.Log
import androidx.work.DelegatingWorkerFactory
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.petrolplus.pos.BuildConfig
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.network.repository.GatewayServerRepositoryImpl
import ru.petrolplus.pos.network.ssl.NoSSLv3SocketFactory
import ru.petrolplus.pos.network.ssl.SSLContextFactory
import ru.petrolplus.pos.network.ssl.TrustAllX509TrustManager
import ru.petrolplus.pos.networkapi.GatewayServerApi
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.networkapi.auth.GatewayAuthenticationUtil
import ru.petrolplus.pos.networkworker.executor.GatewayExchangeExecutor
import ru.petrolplus.pos.networkworker.executor.GatewayExchangeExecutorApi
import ru.petrolplus.pos.networkworker.worker.GatewayConfigFactory
import ru.petrolplus.pos.networkworker.worker.GatewayConfigScheduler
import ru.petrolplus.pos.networkworker.worker.GatewayConfigScheduler.Companion.REMOTE_CONFIG_WORKER
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.util.constants.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.net.ssl.SSLSocketFactory

@Module
object NetworkModule {

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
            .apply {
                if (BuildConfig.DEBUG) addInterceptor(
                    HttpLoggingInterceptor().also { it.level = HttpLoggingInterceptor.Level.BODY }
                )
            }
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
    @Named(REMOTE_CONFIG_WORKER)
    fun providesGatewayExchangeExecutor(
        gatewayServer: GatewayServerApi,
        p7LibCallbacks: IP7LibCallbacks,
        p7LibRepository: IP7LibRepository
    ): GatewayExchangeExecutorApi =
        GatewayExchangeExecutor(gatewayServer, p7LibCallbacks, p7LibRepository)

    @[Provides AppScope]
    @Named(REMOTE_CONFIG_WORKER)
    fun providesGatewayConfigScheduler(): GatewayConfigScheduler = GatewayConfigScheduler()

    @[Provides AppScope]
    @Named(REMOTE_CONFIG_WORKER)
    fun providesGatewayConfigFactory(@Named(REMOTE_CONFIG_WORKER) gatewayExchangeExecutor: GatewayExchangeExecutorApi): GatewayConfigFactory =
        GatewayConfigFactory(gatewayExchangeExecutor)

    @[Provides AppScope]
    @Named(REMOTE_CONFIG_WORKER)
    fun providesDelegatingWorkerFactory(@Named(REMOTE_CONFIG_WORKER) remoteConfigFactory: GatewayConfigFactory): DelegatingWorkerFactory =
        DelegatingWorkerFactory().apply {
            addFactory(remoteConfigFactory)
        }
}