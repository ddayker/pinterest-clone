package com.dayker.pexels.presentation.bookmarks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.dayker.pexels.R
import com.dayker.pexels.domain.model.Image
import com.dayker.pexels.presentation.home.components.shimmerEffect

@Composable
fun BookmarksImageItem(
    modifier: Modifier = Modifier,
    image: Image,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(image.src)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = null,
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Error || state is AsyncImagePainter.State.Loading) {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        MaterialTheme.colorScheme.secondary
                    )
                    .shimmerEffect(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.image),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }

        } else {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
            ) {
                this@SubcomposeAsyncImage.SubcomposeAsyncImageContent(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Fit
                )
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(33.dp)
                        .background(
                            shape = RectangleShape,
                            brush = SolidColor(Color.Black),
                            alpha = 0.4f
                        )
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = image.photographer,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}