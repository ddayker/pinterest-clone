package com.dayker.pexels.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.dayker.pexels.core.navigation.ComposeApplication
import com.dayker.pexels.core.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.startDestinationRoute.value == null
            }
        }
        setContent {
            AppTheme {
                Surface {
                    val navController = rememberNavController()
                    val windowSize = calculateWindowSizeClass(this@MainActivity)
                    val startDestination =
                        viewModel.startDestinationRoute.collectAsStateWithLifecycle().value
                    startDestination?.let { route ->
                        ComposeApplication(
                            navController = navController,
                            windowSize = windowSize,
                            startDestinationRoute = route
                        )
                    }
                }
            }
        }
    }
}

