package ru.petroplus.pos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.navigation.compose.rememberNavController
import ru.petroplus.pos.navigation.BottomBarItem
import ru.petroplus.pos.navigation.BottomNavigationController
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.ui.navigation.NavigationController

class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomNavWithBadgesTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationController(
                            items = listOf(
                                BottomBarItem(
                                    name = "Дебит",
                                    route = Screens.DebitScreen.route,
                                    icon = Icons.Default.ShoppingCart
                                ),
                                BottomBarItem(
                                    name = "Возврат",
                                    route = Screens.RefundScreen.route,
                                    icon = Icons.Default.Refresh,
                                    badgeCount = 0
                                ),
                                BottomBarItem(
                                    name = "Настройки",
                                    route = Screens.SettingsScreen.route,
                                    icon = Icons.Default.Settings,
                                    badgeCount = 0
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    NavigationController(navController = navController)
                }
            }
        }
    }
}
