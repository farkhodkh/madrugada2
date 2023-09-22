package ru.petrolplus.pos

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import org.bouncycastle.jce.provider.BouncyCastleProvider
import ru.petrolplus.pos.di.AppComponent
import ru.petrolplus.pos.di.AppComponentDependencies
import ru.petrolplus.pos.di.DaggerAppComponent
import ru.petrolplus.pos.networkworker.worker.GatewayConfigScheduler
import ru.petrolplus.pos.resources.ResourceHelper
import java.net.CookieHandler
import java.net.CookieManager
import java.security.Security
import javax.inject.Inject
import javax.inject.Named

class App : Application() {

    @Inject
    @Named(GatewayConfigScheduler.REMOTE_CONFIG_WORKER)
    lateinit var workerFactory: DelegatingWorkerFactory

    @Inject
    @Named(GatewayConfigScheduler.REMOTE_CONFIG_WORKER)
    lateinit var workerScheduler: GatewayConfigScheduler

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

        ResourceHelper.setContext(applicationContext)

        initSSLDependencies()

        appComponent.inject(this)

        initWorkManager()
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

    /**
     * Инициализация Worker для регулярной отправки данных в Шлюз
     */
    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        )

        workerScheduler.scheduleWorker(this)
    }
}

val Context.appComponent: AppComponent
    get() = App.appComponent