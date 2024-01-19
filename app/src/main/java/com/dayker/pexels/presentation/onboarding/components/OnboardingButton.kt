package com.dayker.pexels.presentation.onboarding.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R

@Composable
fun OnboardingButton(
    modifier: Modifier = Modifier,
    rotation: Float = 0f,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .size(20.dp)
            .background(
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        onClick = {
            onClick()
        }) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.back),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.rotate(rotation)
        )
    }
}