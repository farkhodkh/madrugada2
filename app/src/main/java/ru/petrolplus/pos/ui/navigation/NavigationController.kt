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
            val debitScreenComponent =
                mainScreenComponent.debitScreenComponentFactory().create(LocalSavedStateRegistryOwner.current)
            DebitScreen(debitScreenComponent.getViewModelFactory()) { screen ->
                navController.navigate(screen)
            }
        }

        composable(Screens.SettingsScreen.route) {
            val mainScreenComponent = (LocalContext.current as MainActivity).mainScreenSubcomponent
            val settingsScreenComponent =
                mainScreenComponent.settingScreenComponentFactory().create(LocalSavedStateRegistryOwner.current)
            SettingsScreen(settingsScreenComponent.getViewModelFactory()) { screen ->
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