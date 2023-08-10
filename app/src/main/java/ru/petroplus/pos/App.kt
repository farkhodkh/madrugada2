package ru.petroplus.pos

import android.app.Application
import android.content.Context
import org.bouncycastle.jce.provider.BouncyCastleProvider
import ru.petroplus.pos.di.AppComponent
import ru.petroplus.pos.di.AppComponentDependencies
import ru.petroplus.pos.di.DaggerAppComponent
import java.security.Security

class App : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        App.appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .appComponentDependencies(AppComponentDependenciesImpl())
            .build()

        Security.removeProvider("BC")
        Security.addProvider(BouncyCastleProvider());
    }

    private inner class AppComponentDependenciesImpl: AppComponentDependencies {
        override val context: Context = this@App
    }
}

val Context.appComponent: AppComponent
    get() = App.appComponent