package ru.petroplus.pos

import android.app.Application
import android.content.Context
import org.bouncycastle.jce.provider.BouncyCastleProvider
import ru.petroplus.pos.di.AppComponent
import ru.petroplus.pos.di.AppComponentDependencies
import ru.petroplus.pos.di.DaggerAppComponent
import java.net.CookieHandler
import java.net.CookieManager
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

        initSSLDependencies()
    }

    private inner class AppComponentDependenciesImpl: AppComponentDependencies {
        override val context: Context = this@App
    }

    private fun initSSLDependencies() {
        /**
         * Инжект провайдера для SSL
         */
        Security.removeProvider("BC")
        Security.addProvider(BouncyCastleProvider())

        /**
         * Инициализация менеджера по умолчания для Cookie
         */
        CookieHandler.setDefault(CookieManager())
    }
}

val Context.appComponent: AppComponent
    get() = App.appComponent