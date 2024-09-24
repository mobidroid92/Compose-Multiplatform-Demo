package com.myapplication.pressentation.characters

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.myapplication.providers.koinViewModel
import kotlinx.coroutines.flow.StateFlow

const val KEY_PREFIX_NAME = "name"
const val KEY_PREFIX_IMAGE = "image"

@Composable
fun CharactersParentRoot() {

    val viewModel = koinViewModel<CharactersParentViewModel>()
    val navController = rememberNavController()

    val destinationChangedListener = NavController.OnDestinationChangedListener { controller, _, _ ->
        viewModel.handleAction(
            Actions.UpdateIsBackEnabled(controller.previousBackStackEntry)
        )
    }

    DisposableEffect(key1 = true) {
        navController.addOnDestinationChangedListener(destinationChangedListener)
        onDispose {
            navController.removeOnDestinationChangedListener(destinationChangedListener)
        }
    }

    CharactersParent(
        viewModel.uiState,
        navController,
        viewModel::handleAction
    )
}

@Composable
fun CharactersParent(
    uiStateFlow: StateFlow<UiState>,
    navController: NavHostController,
    onAction: (Actions) -> Unit,
) {

    val uiState by uiStateFlow.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = uiState.topAppBarTitle) },
                navigationIcon = if (uiState.isBackEnabled) {
                    {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back Button"
                            )
                        }
                    }
                } else {
                    null
                }
            )
        }
    ) { paddingValues ->
        navigationStack(paddingValues, navController, onAction)
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun navigationStack(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onAction: (Actions) -> Unit
) {
    SharedTransitionLayout {
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = CharactersGraph.InnerGraph.ListScreen,
        ) {
            charactersGraph(
                navController,
                this@SharedTransitionLayout,
                onAction
            )
        }
    }
}