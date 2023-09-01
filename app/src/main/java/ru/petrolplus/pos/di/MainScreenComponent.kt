package ru.petrolplus.pos.di

import dagger.Subcomponent
import ru.petrolplus.pos.persitence.ReceiptPersistence
import ru.petrolplus.pos.persitence.ServicesPersistence
import ru.petrolplus.pos.persitence.SettingsPersistence
import ru.petrolplus.pos.persitence.TransactionsPersistence
import ru.petrolplus.pos.persitence.di.MappersModule
import ru.petrolplus.pos.persitence.di.PersistenceModule
import ru.petrolplus.pos.room.di.RoomModule
import ru.petrolplus.pos.core.MainScreenScope
import ru.petrolplus.pos.ui.main.MainActivity

@MainScreenScope
@Subcomponent(
    modules = [
        RoomModule::class,
        PersistenceModule::class,
        MappersModule::class,
        MainScreenModule::class
    ]
)
interface MainScreenComponent {

    val receiptPersistence: ReceiptPersistence
    val transactionsPersistence: TransactionsPersistence
    val settingsPersistence: SettingsPersistence
    val servicesPersistence: ServicesPersistence

    fun inject(mainActivity: MainActivity)

    @Subcomponent.Builder
    interface Builder {
        fun mainModule(module: MainScreenModule): Builder
        fun roomModule(module: RoomModule): Builder
        fun mappersModule(module: MappersModule): Builder
        fun persistenceModule(module: PersistenceModule): Builder

        fun build(): MainScreenComponent
    }
}