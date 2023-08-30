package ru.petrolplus.pos.ui.main

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
import ru.petrolplus.pos.App
import ru.petrolplus.pos.ui.R
import ru.petrolplus.pos.blockingScreen.StartingApplicationBlockingScreen
import ru.petrolplus.pos.dialogs.ConfigurationFileRequiredDialog
import ru.petrolplus.pos.dialogs.FilePickerDialog
import ru.petrolplus.pos.di.MainScreenComponent
import ru.petrolplus.pos.di.MainScreenModule
import ru.petrolplus.pos.navigation.BottomBarItem
import ru.petrolplus.pos.navigation.BottomNavigationController
import ru.petrolplus.pos.navigation.Screens
import ru.petrolplus.pos.ui.BottomNavWithBadgesTheme
import ru.petrolplus.pos.ui.navigation.NavigationController
import ru.petrolplus.pos.util.constants.Constants
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    lateinit var mainScreenSubcomponent: MainScreenComponent

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainScreenSubcomponent = App.appComponent.mainScreenComponentBuilder()
            .mainModule(MainScreenModule())
            .roomModule(RoomModule())
            .mappersModule(MappersModule())
            .persistenceModule(PersistenceModule())
            .build()

        mainScreenSubcomponent.inject(this)

        setContent {
            val viewState by viewModel.viewState.collectAsState()

            when (viewState) {
                MainScreenState.StartingState -> {
                    StartingApplicationBlockingScreen()
                }

                MainScreenState.CheckingSettingsState -> {
                    viewModel.setupConfiguration(Constants.CONFIG_FILE_NAME)
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
