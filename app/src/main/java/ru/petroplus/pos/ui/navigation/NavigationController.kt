package ru.petroplus.pos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.petroplus.pos.App
import ru.petroplus.pos.appComponent
import ru.petroplus.pos.mainscreen.ui.configuration.ConfigurationCheckScreen
import ru.petroplus.pos.mainscreen.ui.debit.DebitScreen
import ru.petroplus.pos.mainscreen.ui.debit.DebitViewModel
import ru.petroplus.pos.mainscreen.ui.settings.SettingsScreen
import ru.petroplus.pos.mainscreen.ui.settings.SettingsViewModel
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.ui.main.MainActivity
import ru.petroplus.pos.ui.views.RefundScreen

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
                        settingsPersistence = mainScreenComponent.settingsPersistence
                    )
                )
            )
        }

        composable(Screens.SettingsScreen.route) {
            SettingsScreen(
                viewModel(
                    factory = SettingsViewModel.provideFactory(
                        (LocalContext.current as MainActivity).mainScreenSubcomponent.servicesPersistence,
                        owner = LocalSavedStateRegistryOwner.current
                    )
                )
            ) { screen ->
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