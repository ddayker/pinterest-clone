package com.dayker.pexels.auth.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dayker.pexels.R
import com.dayker.pexels.auth.presentation.components.ErrorSnackbar
import com.dayker.pexels.auth.presentation.components.GoogleSignInButton
import com.dayker.pexels.core.navigation.graphs.Graph
import com.dayker.pexels.core.util.Container
import kotlinx.coroutines.launch


@Composable
fun AuthenticationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == ComponentActivity.RESULT_OK) {
                viewModel.onEvent(AuthScreenEvent.OnSignInRequest(intent = result.data))
            }
        }
    )

    Container(viewModel.actionFlow) { action ->
        when (action) {
            is AuthScreenAction.ShowErrorMessage -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = action.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            is AuthScreenAction.ShowSignInRequest -> {
                launcher.launch(
                    IntentSenderRequest.Builder(
                        action.intentSender ?: return@Container
                    ).build()
                )
            }

            AuthScreenAction.OpenHomeScreen -> {
                navController.navigate(Graph.MAIN_GRAPH_ROUTE)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            ) { data ->
                ErrorSnackbar(message = data.visuals.message)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 30.dp),
                text = buildAnnotatedString {
                    append(stringResource(R.string.explore_))
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(stringResource(R.string.pexels))
                    }
                    append(stringResource(R.string.now))
                }
            )
            GoogleSignInButton(
                modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)
            ) {
                viewModel.onEvent(AuthScreenEvent.OnSignInClicked)
            }
        }
    }
}