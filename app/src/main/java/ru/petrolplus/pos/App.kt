package ru.petrolplus.pos

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.DelegatingWorkerFactory
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.bouncycastle.jce.provider.BouncyCastleProvider
import ru.petrolplus.pos.core.errorhandling.PosCoroutineExceptionHandler
import ru.petrolplus.pos.di.AppComponent
import ru.petrolplus.pos.di.AppComponentDependencies
import ru.petrolplus.pos.di.DaggerAppComponent
import ru.petrolplus.pos.networkworker.worker.GatewayConfigScheduler
import ru.petrolplus.pos.resources.ResourceHelper
import ru.petrolplus.pos.util.ErrorLogger
import java.net.CookieHandler
import java.net.CookieManager
import java.security.Security
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named

class App : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

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
        if (BuildConfig.DEBUG) initLogger(appComponent.logger)
        Locale.setDefault(Locale("ru"))
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

    private fun initLogger(logger: ErrorLogger) {
        PosCoroutineExceptionHandler.errorsRelay
            .onEach { logger.log(it) }
            .launchIn(applicationScope)

    }
}

val Context.appComponent: AppComponent
    get() = App.appComponent