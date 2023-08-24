package ru.petroplus.pos.di

import dagger.Module
import ru.petroplus.pos.mainscreen.di.MainScreenComponent

@Module(subcomponents = [MainScreenComponent::class])
object SubcomponentModule{}