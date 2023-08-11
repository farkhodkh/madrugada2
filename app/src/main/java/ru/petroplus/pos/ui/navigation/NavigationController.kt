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
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.navigation.SettingsScreen
import ru.petroplus.pos.ui.views.RefundScreen

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.DebitScreen.route) {

        composable(
            route = Screens.DebitScreen.route
        ) {
            DebitScreen(
                onClickListener = { screen ->
                navController.navigate(screen)
            },
            viewModel = viewModel(
                factory = DebitViewModel.provideFactory(
                    (LocalContext.current.applicationContext as App).appComponent.readerRepository,
                    (LocalContext.current.applicationContext as App).appComponent.gatewayServerRepository,
                    owner = LocalSavedStateRegistryOwner.current
                )
            )
            )
        }

        composable(Screens.SettingsScreen.route) {
            SettingsScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
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