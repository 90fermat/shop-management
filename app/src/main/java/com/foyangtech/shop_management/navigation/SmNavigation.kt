package com.foyangtech.shop_management.navigation

import android.Manifest
import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.foyangtech.shop_management.*
import com.foyangtech.shop_management.common.snackbar.SnackbarManager
import com.foyangtech.shop_management.ui.components.PermissionDialog
import com.foyangtech.shop_management.ui.components.RationaleDialog
import com.foyangtech.shop_management.ui.components.shopScreens
import com.foyangtech.shop_management.ui.screens.home.HomeScreen
import com.foyangtech.shop_management.ui.screens.login.LoginScreen
import com.foyangtech.shop_management.ui.screens.settings.SettingsScreen
import com.foyangtech.shop_management.ui.screens.sign_up.SignUpScreen
import com.foyangtech.shop_management.ui.screens.splash.SplashScreen
import com.foyangtech.shop_management.ui.theme.ShopManagementTheme
import com.foyangtech.shop_management.util.HOME_SCREEN
import com.foyangtech.shop_management.util.LOGIN_SCREEN
import com.foyangtech.shop_management.util.SETTINGS_SCREEN
import com.foyangtech.shop_management.util.SIGN_UP_SCREEN
import com.foyangtech.shop_management.util.SPLASH_SCREEN
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.CoroutineScope

@Composable
@ExperimentalMaterial3Api
fun ShopManagementComposeUi() {
    ShopManagementTheme {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            RequestNotificationPermissionDialog()
        }*/

        Surface(color = MaterialTheme.colorScheme.background) {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = appState.snackbarHostState,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                },
                bottomBar = {
                    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    val route = currentDestination?.route?.split("/")?.get(0)
                    val acceptedRoutes = shopScreens.map { it.route }

                    AnimatedVisibility(
                        visible = route in acceptedRoutes,
                        enter = slideInVertically(initialOffsetY = { it }),
                        exit = slideOutVertically(targetOffsetY = { it }),
                    ) {
                       NavigationBar {
                            shopScreens.forEach { screen ->
                                NavigationBarItem(
                                    selected = currentDestination?.hierarchy?.any {
                                        if (it.route != null) it.route!!.contains(screen.route)
                                        else false
                                    } == true,
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    onClick = {
                                        navBackStackEntry?.arguments?.getString("shopId")?.let {
                                                id -> appState.navigate("${screen.route}/$id")
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = SPLASH_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {

                    shopManagementGraph(appState)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}

@Composable
fun rememberAppState(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) =
    remember(navController, snackbarManager, resources, coroutineScope) {
        RootAppState(
            snackbarHostState, navController,
            snackbarManager, resources, coroutineScope
        )
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.shopManagementGraph(appState: RootAppState) {
    composable(SPLASH_SCREEN) {
        SplashScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SETTINGS_SCREEN) {
        SettingsScreen(
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(SIGN_UP_SCREEN) {
        SignUpScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(HOME_SCREEN) {
        HomeScreen(openScreen = { route -> appState.navigate(route) })
    }

    shopGraph(appState)
}
