package com.dayker.pexels.presentation.profile.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.dayker.pexels.R

@Composable
fun LogOutFab(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    ExtendedFloatingActionButton(
        modifier = modifier,
        onClick = onClicked,
        containerColor = MaterialTheme.colorScheme.secondary,
        text = {
            Text(
                text = stringResource(R.string.log_out),
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.labelMedium,
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.logout_icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onBackground
            )
        })
}