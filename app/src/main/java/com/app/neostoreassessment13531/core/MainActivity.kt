package com.app.neostoreassessment13531.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.app.neostoreassessment13531.core.navigation.Route
import com.app.neostoreassessment13531.core.snackbar.SnackBarController
import com.app.neostoreassessment13531.core.ui.theme.AppTheme
import com.app.neostoreassessment13531.core.util.CollectFlowEvents
import com.app.neostoreassessment13531.neostore.presentation.register_user.view.UiScreenRegisterUser
import com.app.neostoreassessment13531.neostore.presentation.user_details.view.UiScreenUserDetail
import com.app.neostoreassessment13531.neostore.presentation.user_list.view.UiScreenUsersList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NeoStoreApp() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    CollectFlowEvents(SnackBarController.events) {
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(message = it.message, duration = SnackbarDuration.Short)
        }
    }
    CompositionLocalProvider(LocalNavController provides navController) {
        Box {
            SharedTransitionLayout {
                NavHost(
                    navController = navController,
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding(),
                    startDestination = Route.UserList
                ) {
                    composable<Route.UserList> {
                        UiScreenUsersList(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable
                        )
                    }
                    composable<Route.UserDetails> {
                        val user: Route.UserDetails = it.toRoute()
                        UiScreenUserDetail(
                            user.userId,
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable
                        )
                    }
                    composable<Route.RegisterUser> {
                        UiScreenRegisterUser()
                    }
                }
            }
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopCenter),
                snackbar = { snackBarData ->
                    Snackbar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .windowInsetsTopHeight(WindowInsets.statusBars.add(WindowInsets(top = 60.dp)))
                        ,
                        shape = RectangleShape,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxSize()
                                .safeDrawingPadding()
                            ,
                            text = snackBarData.visuals.message,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            )
        }
    }
}





