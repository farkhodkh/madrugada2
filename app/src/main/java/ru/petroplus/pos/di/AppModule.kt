package ru.petroplus.pos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petroplus.pos.core.AppScope
import ru.petroplus.pos.p7Lib.impl.P7LibRepositoryImpl
import ru.petroplus.pos.p7LibApi.IP7LibRepository
import ru.petroplus.pos.ui.main.MainActivityViewModel

@Module
object AppModule{
    @AppScope
    @Provides
    fun provideContext(context: Context): Context = context

    @[Provides AppScope]
    fun providesP7LibRepository(): IP7LibRepository = P7LibRepositoryImpl()

    @[Provides AppScope]
    fun providesMainActivityViewModel(repository: IP7LibRepository): MainActivityViewModel = MainActivityViewModel(repository)
}