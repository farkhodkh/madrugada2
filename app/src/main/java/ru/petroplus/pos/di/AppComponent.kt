package ru.petroplus.pos.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.ui.main.MainActivity
import ru.petroplus.pos.mainscreen.di.MainScreenComponentDependencies
import ru.petroplus.pos.p7Lib.di.P7LibComponentDependencies

@Component(
    modules = [AppModule::class],
    dependencies = [AppComponentDependencies::class]
)
@AppScope
interface AppComponent : MainScreenComponentDependencies, P7LibComponentDependencies {
    //override val libraryRepository: IP7LibRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appComponentDependencies(dependencies: AppComponentDependencies): Builder

        fun build(): AppComponent
    }

    fun inject(application: MainActivity)
}