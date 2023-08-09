package ru.petroplus.pos.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.ui.main.MainActivity
import ru.petroplus.pos.mainscreen.di.MainScreenComponentDependencies
import ru.petroplus.pos.network.di.NetworkComponentDependencies
import ru.petroplus.pos.networkapi.GatewayServerApi
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.p7Lib.di.P7LibComponentDependencies
import ru.petroplus.pos.sdkapi.CardReaderRepository

@Component(
    modules = [AppModule::class],
    dependencies = [AppComponentDependencies::class]
)
@AppScope
interface AppComponent : MainScreenComponentDependencies, P7LibComponentDependencies, NetworkComponentDependencies
{
    val readerRepository: CardReaderRepository
    val gatewayServerRepository: GatewayServerRepositoryApi
//    override var clientBuilder: OkHttpClient.Builder
//    override var requestBuilder: Request.Builder
//    override var retrofit: Retrofit

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appComponentDependencies(dependencies: AppComponentDependencies): Builder

        fun build(): AppComponent
    }

    fun inject(application: MainActivity)
}