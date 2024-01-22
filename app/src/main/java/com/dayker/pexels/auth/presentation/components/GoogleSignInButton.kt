package com.dayker.pexels.auth.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.dayker.pexels.R

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.animation_google_logo))
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .height(80.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .animatedBorder(
                    borderColors = listOf(
                        Color(66, 133, 244),
                        Color(234, 66, 53),
                        Color(251, 180, 48),
                        Color(52, 168, 83)
                    ),
                    backgroundColor = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    borderWidth = 2.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            LottieAnimation(
                composition = composition,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(0.2f),
                speed = 1f,
                iterations = LottieConstants.IterateForever,
            )
            Text(
                text = stringResource(R.string.sign_in_with_google),
                modifier = Modifier.padding(start = 10.dp),
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}