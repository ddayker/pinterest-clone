package com.dayker.pexels.presentation.details.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R

@Composable
fun DetailsBottomBar(
    modifier: Modifier = Modifier,
    onDownloadClicked: () -> Unit,
    onBookmarkClicked: () -> Unit,
    isBookmark: Boolean
) {
    Row(
        modifier = modifier
            .windowInsetsPadding(insets = WindowInsets.navigationBars)
            .padding(start = 24.dp, end = 24.dp, bottom = 24.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .height(48.dp)
                    .width(180.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        MaterialTheme.colorScheme.secondary
                    )
            ) {
                Text(
                    modifier = Modifier.padding(start = 65.dp),
                    text = stringResource(R.string.download),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            IconButton(
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = {
                    onDownloadClicked()
                }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.download),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        val color by animateColorAsState(
            targetValue = if (isBookmark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
            animationSpec = tween(durationMillis = 1000), label = ""
        )
        IconButton(
            modifier = Modifier.size(48.dp),
            colors = IconButtonDefaults.iconButtonColors(containerColor = color),
            onClick = {
                onBookmarkClicked()
            }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.favorite),
                contentDescription = null
            )
        }
    }
}