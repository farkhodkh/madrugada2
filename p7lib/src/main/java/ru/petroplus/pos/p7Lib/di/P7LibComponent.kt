package ru.petroplus.pos.p7Lib.di

import dagger.Component
import ru.petroplus.pos.core.P7LibScope

@[P7LibScope Component(dependencies = [P7LibComponentDependencies::class])]
interface P7LibComponent {
    @Component.Builder
    interface Builder {

        fun dependencies(dependencies: P7LibComponentDependencies): Builder

        fun build(): P7LibComponent

    }
}