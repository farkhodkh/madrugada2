package ru.petroplus.pos.mainscreen.di

import dagger.Component
import ru.petroplus.pos.core.MainScreenScope

@MainScreenScope
@Component(dependencies = [MainScreenComponentDependencies::class])
interface MainScreenComponent {

    //    fun inject(fragment: TestFragment)
    //
    @Component.Builder
    interface Builder {

        fun deps(deps: MainScreenComponentDependencies): Builder

        fun build(): MainScreenComponent
    }
}