package com.dayker.pexels.presentation.onboarding.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dayker.pexels.presentation.onboarding.components.pages.OnboardingContentPage
import com.dayker.pexels.presentation.onboarding.components.pages.OnboardingFinishPage
import com.dayker.pexels.presentation.onboarding.components.pages.OnboardingStartPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingPager(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onGetStartedClicked: () -> Unit
) {
    HorizontalPager(state = pagerState, modifier = modifier) { index ->
        when (index) {
            0 -> {
                OnboardingStartPage()
            }

            1 -> {
                OnboardingContentPage()
            }

            2 -> {
                OnboardingFinishPage {
                    onGetStartedClicked()
                }
            }

            else -> {}
        }
    }
}