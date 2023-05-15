package ru.petroplus.pos.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petroplus.pos.AppScope

@Module
object AppModule{
    @AppScope
    @Provides
    fun provideContext(context: Context): Context = context
}