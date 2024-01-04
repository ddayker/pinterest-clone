package com.dayker.pexels.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.dayker.pexels.R

@Composable
fun SearchingBar(
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    query: String,
) {
    var text by remember(key1 = query) { mutableStateOf(query) }
    val focusManager = LocalFocusManager.current
    TextField(
        value = text,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                focusManager.clearFocus()
                onValueChange(text)
            },
        ),
        singleLine = true,
        modifier = modifier
            .windowInsetsPadding(insets = WindowInsets.statusBars)
            .height(52.dp),
        shape = RoundedCornerShape(50.dp),
        onValueChange = {
            text = it
            onValueChange(it)
        },
        textStyle = MaterialTheme.typography.bodyMedium,
        placeholder = {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.search),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null,
                modifier = Modifier.size(17.4.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            AnimatedVisibility(text.isNotEmpty()) {
                Icon(
                    painter = painterResource(id = R.drawable.close_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(14.6.dp)
                        .clickable {
                            text = ""
                            onValueChange(text)
                            focusManager.clearFocus()
                        },
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}
