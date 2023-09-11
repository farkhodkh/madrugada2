package ru.petrolplus.pos.di

import dagger.Subcomponent
import ru.petrolplus.pos.persitence.di.MappersModule
import ru.petrolplus.pos.persitence.di.PersistenceModule
import ru.petrolplus.pos.room.di.RoomModule
import ru.petrolplus.pos.core.MainScreenScope
import ru.petrolplus.pos.mainscreen.ui.debit.di.DebitScreenComponent
import ru.petrolplus.pos.mainscreen.ui.settings.di.SettingsScreenComponent
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
    fun inject(mainActivity: MainActivity)
    fun settingScreenComponentFactory(): SettingsScreenComponent.Factory
    fun debitScreenComponentFactory(): DebitScreenComponent.Factory

    @Subcomponent.Builder
    interface Builder {
        fun mainModule(module: MainScreenModule): Builder
        fun roomModule(module: RoomModule): Builder
        fun mappersModule(module: MappersModule): Builder
        fun persistenceModule(module: PersistenceModule): Builder

        fun build(): MainScreenComponent
    }
}