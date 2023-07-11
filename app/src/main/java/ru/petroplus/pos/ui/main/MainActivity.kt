package ru.petroplus.pos.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import ru.petroplus.pos.App
import ru.petroplus.pos.blockingScreen.StartingApplicationBlockingScreen
import ru.petroplus.pos.dialogs.ConfigurationFileRequiredDialog
import ru.petroplus.pos.dialogs.FilePickerDialog
import ru.petroplus.pos.navigation.BottomBarItem
import ru.petroplus.pos.navigation.BottomNavigationController
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.ui.BottomNavWithBadgesTheme
import ru.petroplus.pos.ui.navigation.NavigationController
import ru.petroplus.pos.util.constants.Constants
import java.io.FileNotFoundException
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        setContent {
            val viewState by viewModel.viewState.collectAsState()

            when (viewState) {
                MainScreenState.StartingState -> {
                    StartingApplicationBlockingScreen()
                }

                MainScreenState.CheckingSettingsState -> {
                    try {
                        val fis = applicationContext.openFileInput(Constants.CONFIG_FILE_NAME)
                        viewModel.readConfigurationFile(fis)
                    } catch (ex: FileNotFoundException) {
                        viewModel.readConfigurationFile(null)
                    }
                }

                MainScreenState.CheckingSuccessState -> {
                    val navController = rememberNavController()

                    BottomNavWithBadgesTheme {
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

                is MainScreenState.CheckingSettingsError -> {
                    with((viewState as MainScreenState.CheckingSettingsError)) {
                        val message = if (this.errorMessageId == 0) {
                            this.errorMessage
                        } else {
                            this.errorMessage
                        }
                        Toast.makeText(
                            this@MainActivity,
                            message,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }

                MainScreenState.DownloadIniFileState -> {
                    FilePickerDialog { configurationContent ->
                        viewModel.configurationFileDownloaded(baseContext, configurationContent)
                    }
                }

                MainScreenState.NoIniFileError -> {
                    ConfigurationFileRequiredDialog { result ->
                        if (result) {
                            viewModel.configurationFileDownloadRequired()
                        } else {
                            viewModel.configurationDidnotLoaded()
                        }
                    }
                }
            }
        }
    }
}
