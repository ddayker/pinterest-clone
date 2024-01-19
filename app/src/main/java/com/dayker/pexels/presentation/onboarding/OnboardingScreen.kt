package com.dayker.pexels.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dayker.pexels.core.navigation.graphs.Graph
import com.dayker.pexels.core.util.Container
import com.dayker.pexels.presentation.onboarding.OnboardingViewModel.Companion.PAGER_SIZE
import com.dayker.pexels.presentation.onboarding.components.OnboardingPager
import com.dayker.pexels.presentation.onboarding.components.OnboardingProgress
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {
        PAGER_SIZE
    })

    Container(viewModel.actionFlow) { action ->
        when (action) {
            OnboardingAction.ScrollBackward -> {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                }
            }

            OnboardingAction.ScrollForward -> {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }

            OnboardingAction.GetStarted -> {
                navController.navigate(route = Graph.NAVIGATION_BAR_ROUTE) {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            }
        }
    }
    Scaffold(
        modifier = modifier,
        bottomBar = {
            OnboardingProgress(
                pagerState = pagerState,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .height(90.dp),
                onClick = viewModel::onEvent
            )
        }
    ) { padding ->
        OnboardingPager(
            modifier = Modifier.padding(padding),
            pagerState = pagerState,
            onGetStartedClicked = {
                viewModel.onEvent(event = OnboardingEvent.OnClickedGetStarted)
            }
        )
    }
}