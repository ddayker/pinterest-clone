package com.dayker.pexels.presentation.home.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedItem(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    FilterChip(
        modifier = modifier.height(38.dp),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(100.dp),
        label = {
            Text(
                text = title,
                style = if (isSelected) MaterialTheme.typography.headlineMedium
                else MaterialTheme.typography.titleMedium
            )
        },
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.secondary,
            selectedLabelColor = Color.White,
            labelColor = MaterialTheme.colorScheme.onBackground,

            ),
        border = FilterChipDefaults.filterChipBorder(
            selectedBorderColor = MaterialTheme.colorScheme.primary,
            borderColor = MaterialTheme.colorScheme.secondary
        )
    )
}