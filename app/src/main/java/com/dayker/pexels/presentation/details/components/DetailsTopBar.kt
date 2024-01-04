package com.dayker.pexels.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R


@Composable
fun DetailsTopBar(
    modifier: Modifier = Modifier,
    backButtonAction: () -> Unit,
    name: String
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .padding(top = 17.dp)
            .fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier
                .padding(start = 20.dp)
                .size(40.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(12.dp)
                ),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            onClick = {
                backButtonAction()
            }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.back),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Text(
            text = name,
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
