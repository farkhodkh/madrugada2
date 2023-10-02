package ru.petrolplus.pos.evotorprinter.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.core.AppScope
import ru.petrolplus.pos.evotorprinter.EvotorPrinterRepositoryImpl
import ru.petrolplus.pos.printerapi.PrinterRepository

@Module
class EvatorPrinterModule {
    @[Provides AppScope]
    fun providePrinter(context: Context): PrinterRepository =
        EvotorPrinterRepositoryImpl(context)
}
