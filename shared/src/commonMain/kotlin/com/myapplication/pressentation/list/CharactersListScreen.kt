package com.myapplication.pressentation.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.myapplication.providers.getCharactersListViewModel
import com.myapplication.pressentation.uiModels.CharacterUiModel
import com.seiko.imageloader.rememberAsyncImagePainter

@Composable
fun CharactersListScreen() {

    val viewModel: CharactersListViewModel = getCharactersListViewModel()
    val state = viewModel.state.collectAsState()
    val title = "Compose Multiplatform"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                }
            )
        }
    ) { paddingValues ->
        ScreenContent(state, viewModel, paddingValues)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenContent(
    state: State<CharactersListUiState>,
    viewModel: CharactersListViewModel,
    paddingValues: PaddingValues
) {

    val isShouldShowPullToRefreshIndicator: State<Boolean> = remember {
        derivedStateOf {
            state.value.isLoading && state.value.isFullRefresh
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        isShouldShowPullToRefreshIndicator.value,
        { viewModel.getCharacters(isFullRefresh = true) }
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .padding(paddingValues)
    ) {

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(count = state.value.charactersList.size) { index ->
                handleLoadNextItems(index, state, viewModel)
                RenderRow(index, state)
            }

            renderLoadMoreProgressItem(state)

            renderErrorAndRetryItem(state, viewModel)
        }

        RenderWaiting(state, isShouldShowPullToRefreshIndicator)

        PullRefreshIndicator(
            isShouldShowPullToRefreshIndicator.value,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}

private fun LazyListScope.renderLoadMoreProgressItem(state: State<CharactersListUiState>) {
    if (state.value.isLoading && state.value.isFullRefresh.not()) {
        item {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(8.dp)
            )
        }
    }
}

private fun LazyListScope.renderErrorAndRetryItem(
    state: State<CharactersListUiState>,
    viewModel: CharactersListViewModel
) {
    if (state.value.isShowError && state.value.charactersList.isNotEmpty()) {
        val errorMsg = "Loading data failed"
        val retry = "retry"
        item {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = errorMsg,
                )
                Button(
                    onClick = { viewModel.getCharacters(false) }
                ) {
                    Text(text = retry)
                }
            }
        }
    }
}

@Composable
private fun RenderWaiting(
    state: State<CharactersListUiState>,
    isShouldShowPullToRefreshIndicator: State<Boolean>
) {
    if (isShouldShowPullToRefreshIndicator.value && state.value.charactersList.isEmpty()) {
        val waitingMsg = "Waiting for items to load from server"
        Text(
            text = waitingMsg,
            modifier = Modifier.fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
private fun RenderRow(
    index: Int,
    state: State<CharactersListUiState>
) {
    val item: CharacterUiModel = state.value.charactersList[index]
    CharacterItem(model = item)

    if (index != state.value.charactersList.lastIndex) {
        Divider()
    }
}

@Composable
private fun CharacterItem(model: CharacterUiModel) {
    Row(
        modifier = Modifier
            .height(70.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true),
                onClick = {}
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberAsyncImagePainter(model.image),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(5.dp))
        )
        Text(
            text = model.name,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
    }
}

private fun handleLoadNextItems(
    index: Int,
    state: State<CharactersListUiState>,
    viewModel: CharactersListViewModel
) {
    if (index == state.value.charactersList.lastIndex && state.value.isLoading.not()) {
        viewModel.getCharacters(isFullRefresh = false)
    }
}