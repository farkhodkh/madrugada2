package ru.petrolplus.pos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.petrolplus.pos.App
import ru.petrolplus.pos.appComponent
import ru.petrolplus.pos.mainscreen.ui.configuration.ConfigurationCheckScreen
import ru.petrolplus.pos.mainscreen.ui.debit.DebitScreen
import ru.petrolplus.pos.mainscreen.ui.debit.DebitViewModel
import ru.petrolplus.pos.mainscreen.ui.settings.SettingsScreen
import ru.petrolplus.pos.navigation.Screens
import ru.petrolplus.pos.ui.main.MainActivity
import ru.petrolplus.pos.ui.views.RefundScreen

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.DebitScreen.route) {

        composable(
            route = Screens.DebitScreen.route
        ) {
            val mainScreenComponent = (LocalContext.current as MainActivity).mainScreenSubcomponent
            DebitScreen(
                onClickListener = { screen ->
                    navController.navigate(screen)
                },
                viewModel = viewModel(
                    factory = DebitViewModel.provideFactory(
                        cardReaderRepository = (LocalContext.current.applicationContext as App).appComponent.readerRepository,
                        gatewayServer = (LocalContext.current.applicationContext as App).appComponent.gatewayServerRepository,
                        owner = LocalSavedStateRegistryOwner.current,
                        printerRepository = (LocalContext.current.applicationContext as App).appComponent.printer,
                        receiptPersistence = mainScreenComponent.receiptPersistence,
                        transactionsPersistence = mainScreenComponent.transactionsPersistence,
                        settingsPersistence = mainScreenComponent.settingsPersistence,
                        p7LibRepository = mainScreenComponent.p7LibRepository,
                        p7LibCallbacks = mainScreenComponent.p7LibCallbacks
                    )
                )
            )
        }

        composable(Screens.SettingsScreen.route) {
            val mainScreenComponent = (LocalContext.current as MainActivity).mainScreenSubcomponent
            val settingsScreenComponent =
                mainScreenComponent.settingScreenComponentFactory().create(LocalSavedStateRegistryOwner.current)

            val viewModelFactory = settingsScreenComponent.getViewModelFactory()
            SettingsScreen(viewModelFactory) { screen ->
                navController.navigate(screen)
            }
        }

        composable(Screens.RefundScreen.route) {
            RefundScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
        }

        composable(Screens.ConfigurationCheckScreen.route) {
            ConfigurationCheckScreen()
        }
    }
}