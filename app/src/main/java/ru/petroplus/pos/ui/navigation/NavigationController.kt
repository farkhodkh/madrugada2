package ru.petroplus.pos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.petroplus.pos.mainscreen.ui.MainScreen
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.navigation.SettingsScreen
import ru.petroplus.pos.ui.views.ShoppingChartScreen

@Composable
fun NavigationController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.MainScreen.route) {

        composable(
            route = Screens.MainScreen.route
        ) {
            MainScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
        }

        composable(Screens.SettingsScreen.route) {
            SettingsScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
        }

        composable(Screens.ShoppingChartScreen.route) {
            ShoppingChartScreen(onClickListener = { screen ->
                navController.navigate(screen)
            })
        }

    }
}