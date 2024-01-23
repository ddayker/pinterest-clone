package com.dayker.pexels.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.dayker.pexels.core.navigation.graphs.RootNavigationGraph
import com.dayker.pexels.core.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var isOnboardingFinished: Boolean? = null

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isOnboardingFinished == null
            }
        }

        lifecycleScope.launch {
            viewModel.onBoardingIsCompleted
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { isOnboardingFinished ->
                    this@MainActivity.isOnboardingFinished = isOnboardingFinished
                }
        }

        setContent {
            AppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val isOnboardingCompleted =
                        viewModel.onBoardingIsCompleted.collectAsStateWithLifecycle().value
                    val navController = rememberNavController()
                    val windowSize = calculateWindowSizeClass(this@MainActivity)
                    if (isOnboardingCompleted != null) {
                        RootNavigationGraph(
                            navController = navController,
                            windowSize = windowSize,
                            isOnboardingFinished = isOnboardingCompleted
                        )
                    }
                }
            }
        }
    }
}
