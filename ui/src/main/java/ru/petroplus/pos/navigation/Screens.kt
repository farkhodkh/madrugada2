package ru.petroplus.pos.navigation

sealed class Screens(val route: String) {
    object MainScreen : Screens("main_screen")
    object ShoppingChartScreen : Screens("shopping_chart_screen")
    object SettingsScreen : Screens("setting_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
