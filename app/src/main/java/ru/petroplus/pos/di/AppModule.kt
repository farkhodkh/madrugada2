package ru.petroplus.pos.di

import android.content.Context
import com.google.gson.GsonBuilder
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.petrolplus.pos.room.database.AppDatabase
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.evotorsdk.EvotorSDKRepository
import ru.petroplus.pos.network.auth.GatewayAuthenticationUtil
import ru.petroplus.pos.network.repository.GatewayServerRepositoryImpl
import ru.petroplus.pos.network.ssl.NoSSLv3SocketFactory
import ru.petroplus.pos.network.ssl.SSLContextFactory
import ru.petroplus.pos.network.ssl.TrustAllX509TrustManager
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.p7Lib.impl.P7LibCallbacksImpl
import ru.petroplus.pos.p7Lib.impl.P7LibRepositoryImpl
import ru.petroplus.pos.p7LibApi.IP7LibCallbacks
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.sdkapi.CardReaderRepository
import ru.petroplus.pos.sdkapi.ISDKRepository
import ru.petroplus.pos.ui.main.MainActivityViewModel
import ru.petroplus.pos.util.constants.Constants.GATEWAY_SERVER_ADDRESS_AND_PORT
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLSocketFactory

@Module
object AppModule {
    @[Provides AppScope]
    fun providesP7LibRepository(): IP7LibRepository = P7LibRepositoryImpl()

    @[Provides AppScope]
    fun providesP7LibCallbacks(): IP7LibCallbacks = P7LibCallbacksImpl()

    @[Provides AppScope]
    fun providesMainActivityViewModel(
        repository: IP7LibRepository,
        callBacks: IP7LibCallbacks
    ): MainActivityViewModel = MainActivityViewModel(repository, callBacks)

    @[Provides AppScope]
    fun providesEvotorSDKRepository(context: Context): ISDKRepository = EvotorSDKRepository(context)

    @[Provides AppScope]
    fun providesCardReaderRepository(sdkRepository: ISDKRepository): CardReaderRepository =
        object : CardReaderRepository {
            override val sdkRepository: ISDKRepository
                get() = sdkRepository
        }


    @[Provides AppScope]
    fun providesAppDatabase(context: Context) = AppDatabase.getInstance(context)
}