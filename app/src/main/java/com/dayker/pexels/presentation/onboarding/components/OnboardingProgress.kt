package com.dayker.pexels.presentation.onboarding.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dayker.pexels.presentation.onboarding.OnboardingEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingProgress(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClick: (OnboardingEvent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var animationPlayed by rememberSaveable {
            mutableStateOf(false)
        }
        val currentPercentage = animateFloatAsState(
            targetValue =
            if (animationPlayed) ((pagerState.currentPage + 1).toFloat() / pagerState.pageCount) else 0f,
            animationSpec = tween(durationMillis = 1000), label = ""
        )
        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }
        AnimatedVisibility(
            pagerState.canScrollBackward
        ) {
            OnboardingButton {
                onClick(OnboardingEvent.OnClickedBackward)
            }
        }
        LinearProgressIndicator(
            modifier = Modifier
                .padding(start = 30.dp, end = 30.dp)
                .weight(1f),
            progress = currentPercentage.value,
            color = MaterialTheme.colorScheme.primary
        )
        AnimatedVisibility(
            pagerState.canScrollForward
        ) {
            OnboardingButton(
                rotation = 180f
            ) {
                onClick(OnboardingEvent.OnClickedForward)
            }
        }
    }
}
