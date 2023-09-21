package ru.petrolplus.pos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.petrolplus.pos.mainscreen.ui.configuration.ConfigurationCheckScreen
import ru.petrolplus.pos.mainscreen.ui.debit.DebitScreen
import ru.petrolplus.pos.mainscreen.ui.debit.DebitViewModel
import ru.petrolplus.pos.mainscreen.ui.settings.SettingsScreen
import ru.petrolplus.pos.mainscreen.ui.settings.SettingsViewModel
import ru.petrolplus.pos.navigation.Screens
import ru.petrolplus.pos.mainscreen.ui.refund.RefundScreen
import javax.inject.Inject

/**
 * Класс-обертка для создания NavHost
 * Позволяет передавать сущности (например, фабрики для создания ViewModel) задействуя DI
 */
class PosNavController @Inject constructor(
    private val debitViewModelAssistedFactory: DebitViewModel.DebitViewModelFactory,
    private val settingsViewModelAssistedFactory: SettingsViewModel.SettingsViewModelFactory
) {
    @Composable
    fun SetupNavHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = Screens.DebitScreen.route) {

            composable(route = Screens.DebitScreen.route) {
                DebitScreen(
                    viewModel = viewModel(
                        factory = DebitViewModel.provideFactory(
                            factory = debitViewModelAssistedFactory,
                            owner = LocalSavedStateRegistryOwner.current,
                        )
                    )
                ) { screen ->
                    navController.navigate(screen)
                }
            }

            composable(Screens.SettingsScreen.route) {
                SettingsScreen(
                    viewModel = viewModel(
                        factory = SettingsViewModel.provideFactory(
                            factory = settingsViewModelAssistedFactory,
                            owner = LocalSavedStateRegistryOwner.current
                        )
                    )
                ) { screen ->
                    navController.navigate(screen)
                }
            }

            composable(Screens.RefundScreen.route) {
                RefundScreen { screen -> navController.navigate(screen) }
            }

            composable(Screens.ConfigurationCheckScreen.route) {
                ConfigurationCheckScreen()
            }
        }
    }
}