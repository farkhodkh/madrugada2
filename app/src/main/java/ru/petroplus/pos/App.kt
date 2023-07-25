package ru.petroplus.pos

import android.app.Application
import android.content.Context
import org.bouncycastle.jce.provider.BouncyCastleProvider
import ru.petroplus.pos.di.AppComponent
import ru.petroplus.pos.di.AppComponentDependencies
import ru.petroplus.pos.di.DaggerAppComponent
import ru.petroplus.pos.util.ResourceHelperJava
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

        ResourceHelperJava.context = applicationContext

        Security.removeProvider("BC")
        // Confirm that positioning this provider at the end works for your needs!
        Security.addProvider(BouncyCastleProvider());
    }

    private inner class AppComponentDependenciesImpl: AppComponentDependencies {
        override val context: Context = this@App
    }
}

val Context.appComponent: AppComponent
    get() = App.appComponent
//    = when (this) {
//        is MainActivity -> {
//            App.appComponent
//        }
//        else -> {
//            App.appComponent
////            this.applicationContext.appComponent
//        }
//    }