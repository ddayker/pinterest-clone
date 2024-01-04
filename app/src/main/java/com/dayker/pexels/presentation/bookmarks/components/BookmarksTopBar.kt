package com.dayker.pexels.presentation.bookmarks.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R

@Composable
fun BookmarksTopBar(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .padding(top = 17.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.bookmarks),
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
