package com.dayker.pexels.presentation.onboarding.components.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R
import com.dayker.pexels.presentation.onboarding.components.Animation

@Composable
fun OnboardingStartPage(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = buildAnnotatedString {
                append(stringResource(R.string.welcome_to))
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                    append(stringResource(R.string.pexels))
                }
            }
        )
        Animation(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(top = 50.dp, bottom = 30.dp),
            lottieRes = R.raw.animation_lottie_heart
        )
        Text(
            modifier = Modifier.padding(horizontal = 30.dp),
            text = stringResource(R.string.the_best_free_stock_photos_royalty_free_images_videos_shared_by_creators),
            textAlign = TextAlign.Center
        )
    }
}
