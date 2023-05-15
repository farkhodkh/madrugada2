package ru.petroplus.pos.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.petroplus.pos.AppScope
import ru.petroplus.pos.ui.MainActivity
import ru.petroplus.pos.mainscreen.di.MainScreenComponent

@Component(
    modules = [AppModule::class],
    dependencies = [AppComponentDependencies::class]
)
@AppScope
interface AppComponent : MainScreenComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun appComponentDependencies(dependencies: AppComponentDependencies): Builder

        fun build(): AppComponent
    }

    fun inject(application: MainActivity)
}