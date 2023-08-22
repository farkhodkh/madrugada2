package ru.petroplus.pos.ui.main

import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import ru.petrolplus.pos.persitence.di.MappersModule
import ru.petrolplus.pos.persitence.di.PersistenceModule
import ru.petrolplus.pos.room.di.RoomModule
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.security.ProviderInstaller
import ru.petroplus.pos.App
import ru.petroplus.pos.ui.R
import ru.petroplus.pos.blockingScreen.StartingApplicationBlockingScreen
import ru.petroplus.pos.dialogs.ConfigurationFileRequiredDialog
import ru.petroplus.pos.dialogs.FilePickerDialog
import ru.petroplus.pos.mainscreen.di.MainScreenComponent
import ru.petroplus.pos.navigation.BottomBarItem
import ru.petroplus.pos.navigation.BottomNavigationController
import ru.petroplus.pos.navigation.Screens
import ru.petroplus.pos.ui.BottomNavWithBadgesTheme
import ru.petroplus.pos.ui.navigation.NavigationController
import ru.petroplus.pos.util.ResourceHelper
import ru.petroplus.pos.util.constants.Constants
import java.io.FileNotFoundException
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    lateinit var mainScreenSubcomponent: MainScreenComponent

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        mainScreenSubcomponent = App.appComponent.mainScreenComponentBuilder()
            .roomModule(RoomModule())
            .mappersModule(MappersModule())
            .persistenceModule(PersistenceModule())
            .build()

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
                                            name = stringResource(id = R.string.debit_label),
                                            route = Screens.DebitScreen.route,
                                            icon = Icons.Default.ShoppingCart
                                        ),
                                        BottomBarItem(
                                            name = stringResource(id = R.string.refund_label),
                                            route = Screens.RefundScreen.route,
                                            icon = Icons.Default.Refresh,
                                            badgeCount = 0
                                        ),
                                        BottomBarItem(
                                            name = stringResource(id = R.string.settings_label),
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

        try {
            ProviderInstaller.installIfNeeded(this)
        } catch (e: GooglePlayServicesRepairableException) {
            // Thrown when Google Play Services is not installed, up-to-date, or enabled
            // Show dialog to allow users to install, update, or otherwise enable Google Play services.
            GooglePlayServicesUtil.getErrorDialog(e.connectionStatusCode, this, 0)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e("SecurityException", "Google Play Services not available.")
        }
    }
}
