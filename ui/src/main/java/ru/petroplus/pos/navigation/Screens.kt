package ru.petroplus.pos.navigation

/**
 * Экраны для навигации
 */
sealed class Screens(val route: String) {
    /**
     * Экран для опреации "Дебита"
     */
    object DebitScreen : Screens("debit_screen")

    /**
     * Экран для опреации "Возврата"
     */
    object RefundScreen : Screens("refund_screen")

    /**
     * Экран "Настройки"
     */
    object SettingsScreen : Screens("setting_screen")

    /**
     * Экран загрузки и проверки конфигурации приложения
     */
    object ConfigurationCheckScreen: Screens("configuration_check_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
