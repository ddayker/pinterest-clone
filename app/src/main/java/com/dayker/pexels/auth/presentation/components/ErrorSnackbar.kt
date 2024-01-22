package com.dayker.pexels.auth.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ErrorSnackbar(
    modifier: Modifier = Modifier,
    message: String
) {
    Snackbar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {
        Text(
            text = message,
            color = Color.White,
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 13.sp),
            modifier = Modifier.padding(bottom = 10.dp)
        )
    }
}