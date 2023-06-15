package ru.petroplus.pos.navigation

sealed class Screens(val route: String) {
    object DebitScreen : Screens("debit_screen")
    object RefundScreen : Screens("refund_screen")
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
