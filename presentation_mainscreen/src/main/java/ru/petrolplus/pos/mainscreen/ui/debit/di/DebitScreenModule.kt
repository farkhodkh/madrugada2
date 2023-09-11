package ru.petrolplus.pos.mainscreen.ui.debit.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.savedstate.SavedStateRegistryOwner
import dagger.Module
import dagger.Provides
import ru.petrolplus.pos.mainscreen.ui.debit.DebitViewModel
import ru.petrolplus.pos.networkapi.GatewayServerRepositoryApi
import ru.petrolplus.pos.p7LibApi.IP7LibCallbacks
import ru.petrolplus.pos.p7LibApi.IP7LibRepository
import ru.petrolplus.pos.persitence.ReceiptPersistence
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petrolplus.pos.printerapi.PrinterRepository
import ru.petrolplus.pos.sdkapi.CardReaderRepository

@Module
class DebitScreenModule {
    @Provides
    fun provideDebitViewModelFactory(
        cardReaderRepository: CardReaderRepository,
        printerRepository: PrinterRepository,
        gatewayService: GatewayServerRepositoryApi,
        transactionsPersistence: TransactionsPersistence,
        settingsPersistence: SettingsPersistence,
        receiptPersistence: ReceiptPersistence,
        p7LibRepository: IP7LibRepository,
        p7LibCallbacks: IP7LibCallbacks,
        owner: SavedStateRegistryOwner
    ): AbstractSavedStateViewModelFactory {
        return DebitViewModel.provideFactory(
            cardReaderRepository,
            printerRepository,
            gatewayService,
            transactionsPersistence,
            settingsPersistence,
            owner,
            receiptPersistence,
            p7LibRepository,
            p7LibCallbacks
        )
    }
}
