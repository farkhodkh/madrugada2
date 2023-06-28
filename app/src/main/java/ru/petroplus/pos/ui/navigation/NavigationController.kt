package ru.petroplus.pos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.petroplus.pos.mainscreen.ui.configuration.ConfigurationCheckScreen
import ru.petroplus.pos.mainscreen.ui.debit.DebitScreen
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.navigation.SettingsScreen
import ru.petroplus.pos.ui.views.RefundScreen

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.DebitScreen.route) {

        composable(
            route = Screens.DebitScreen.route
        ) {
            DebitScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
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