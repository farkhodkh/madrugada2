package ru.petrolplus.pos.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.petrolplus.pos.App
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.evotorprinter.di.EvatorPrinterModule
import ru.petrolplus.pos.evotorsdk.di.EvotorSDKModule
import ru.petrolplus.pos.network.di.NetworkComponentDependencies
import ru.petrolplus.pos.network.di.NetworkModule
import ru.petrolplus.pos.p7Lib.di.P7LibComponentDependencies
import ru.petrolplus.pos.util.ErrorLogger

@Component(
    modules = [
        AppModule::class,
        SubcomponentModule::class,
        NetworkModule::class,
        EvatorPrinterModule::class,
        EvotorSDKModule::class,
    ],
    dependencies = [AppComponentDependencies::class],
)
@AppScope
interface AppComponent : MainScreenComponentDependencies, P7LibComponentDependencies, NetworkComponentDependencies {
    val logger: ErrorLogger

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
