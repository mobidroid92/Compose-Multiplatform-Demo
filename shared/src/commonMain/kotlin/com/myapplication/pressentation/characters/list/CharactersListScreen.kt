package com.myapplication.pressentation.characters.list

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.myapplication.di.koinViewModel
import com.myapplication.pressentation.characters.CharactersGraph
import com.myapplication.pressentation.characters.KEY_PREFIX_IMAGE
import com.myapplication.pressentation.characters.KEY_PREFIX_NAME
import com.myapplication.pressentation.characters.uiModels.CharacterUiModel
import com.myapplication.pressentation.common.errorAndRetryRow
import com.myapplication.pressentation.common.loadMoreProgressRow
import com.myapplication.pressentation.common.shimmerLoadingAnimation
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharactersListScreenRoot(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val viewModel = koinViewModel<CharactersListViewModel>()

    CharactersListScreen(
        viewModel.state,
        viewModel::handleActions,
        navController,
        sharedTransitionScope,
        animatedContentScope
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun CharactersListScreen(
    uiStateFlow: Flow<CharactersListUiState>,
    onAction: (Actions) -> Unit,
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {

    val uiState by uiStateFlow.collectAsStateWithLifecycle(CharactersListUiState())

    val pullRefreshState = rememberPullRefreshState(
        refreshing = uiState.isShouldShowPullToRefreshIndicator,
        onRefresh = { onAction(Actions.ReloadCharacters) }
    )

    Box(
        modifier = Modifier.pullRefresh(pullRefreshState)
    ) {

        PullRefreshIndicator(
            refreshing = uiState.isShouldShowPullToRefreshIndicator,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        if (uiState.isShouldShowLoadingShimmer) {
            ShowLoadingShimmer()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {

                items(
                    key = { index -> uiState.charactersList[index].id },
                    count = uiState.charactersList.size
                ) { index ->
                    handleLoadMore(uiState, index, onAction)
                    CharacterRow(
                        index,
                        uiState.charactersList,
                        navController,
                        sharedTransitionScope,
                        animatedContentScope
                    )
                }

                if (uiState.isShouldShowLoadMoreItem) {
                    loadMoreProgressRow()
                }

                if (uiState.isShowErrorItem) {
                    errorAndRetryRow {
                        onAction(Actions.LoadNextCharactersPage)
                    }
                }
            }
        }
    }
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

@Composable
private fun ShowLoadingShimmer() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(
            scrollState,
            enabled = false
        )
    ) {
        repeat(10) {
            Column {
                Row(modifier = Modifier.padding(15.dp)) {
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .clip(shape = CircleShape)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )
                }
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharacterRow(
    index: Int,
    charactersList: List<CharacterUiModel>,
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {

    CharacterItem(
        charactersList[index],
        sharedTransitionScope,
        animatedContentScope,
        navController
    )

    if (index != charactersList.lastIndex) {
        Divider()
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun CharacterItem(
    model: CharacterUiModel,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = {
                    navController.navigate(
                        route = CharactersGraph.InnerGraph.DetailsScreen(
                            charName = model.name, image = model.image
                        )
                    )
                }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                model = model.image,
                contentDescription = null,
                modifier = Modifier
                    .sharedElement(
                        state = sharedTransitionScope.rememberSharedContentState(
                            key = "$KEY_PREFIX_IMAGE-${model.image}"
                        ),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(start = 16.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(5.dp))

            )
            Text(
                text = model.name,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .sharedElement(
                        state = sharedTransitionScope.rememberSharedContentState(
                            key = "$KEY_PREFIX_NAME-${model.name}"
                        ),
                        animatedVisibilityScope = animatedContentScope
                    )
            )
        }
    }
}

private fun handleLoadMore(
    state: CharactersListUiState,
    index: Int,
    onAction: (Actions) -> Unit
) {
    if (index == state.charactersList.lastIndex && state.isLoading.not()) {
        onAction(Actions.LoadNextCharactersPage)
    }
}