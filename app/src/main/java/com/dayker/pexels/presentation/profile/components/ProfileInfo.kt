package com.dayker.pexels.presentation.profile.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dayker.pexels.R
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy

@Composable
fun ProfileInfo(
    modifier: Modifier = Modifier,
    windowSize: WindowSizeClass,
    imageUrl: String,
    name: String,
    email: String
) {
    TwoPane(
        modifier = modifier,
        first = {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.image),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp, bottom = 20.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        },
        second = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(visible = name.isNotBlank()) {
                    Text(
                        text = name,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 30.dp,
                            end = 30.dp
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                AnimatedVisibility(visible = email.isNotBlank()) {
                    Text(
                        text = email,
                        modifier = Modifier.padding(
                            top = 20.dp,
                            start = 30.dp,
                            end = 30.dp
                        ),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        strategy =
        when (windowSize.widthSizeClass) {
            WindowWidthSizeClass.Compact -> VerticalTwoPaneStrategy(0.35f)
            else -> {
                HorizontalTwoPaneStrategy(0.5f)
            }
        },
        displayFeatures = listOf()
    )
}