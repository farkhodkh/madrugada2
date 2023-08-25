package ru.petroplus.pos.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.petroplus.pos.App
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.network.di.NetworkComponentDependencies
import ru.petroplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petroplus.pos.ui.main.MainActivity
import ru.petroplus.pos.p7Lib.di.P7LibComponentDependencies
import ru.petroplus.pos.printerapi.PrinterRepository
import ru.petroplus.pos.sdkapi.CardReaderRepository

@Component(
    modules = [AppModule::class, SubcomponentModule::class, NetworkComponentModule::class],
    dependencies = [AppComponentDependencies::class]
)
@AppScope
interface AppComponent : MainScreenComponentDependencies, P7LibComponentDependencies, NetworkComponentDependencies
{
    val printer: PrinterRepository
    val readerRepository: CardReaderRepository
    val gatewayServerRepository: GatewayServerRepositoryApi

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appComponentDependencies(dependencies: AppComponentDependencies): Builder

        fun build(): AppComponent
    }

    fun inject(application: App)

    fun mainScreenComponentBuilder(): MainScreenComponent.Builder
}