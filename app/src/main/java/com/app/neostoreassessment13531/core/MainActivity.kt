package com.app.neostoreassessment13531.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.neostoreassessment13531.core.navigation.Route
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.neostore.presentation.user_list.view.UiScreenUsersList
import dagger.hilt.android.AndroidEntryPoint

val LocalNavController =
    compositionLocalOf<NavHostController> { error("CompositionLocal LocalNavController not present") }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = false) { NeoStoreApp() }
        }
    }
}

@Composable
fun NeoStoreApp() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            startDestination = Route.UserList
        ) {
            composable<Route.UserList> {
                UiScreenUsersList()
            }
            composable<Route.UserDetails> {
                val id: Route.UserDetails = it.toRoute()

            }
            composable<Route.RegisterUser> {

            }
        }
    }
}



