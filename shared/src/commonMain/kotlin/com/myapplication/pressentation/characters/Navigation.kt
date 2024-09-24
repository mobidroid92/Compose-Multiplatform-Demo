package com.myapplication.pressentation.characters

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.myapplication.pressentation.characters.details.DetailsScreenRoot
import com.myapplication.pressentation.characters.list.CharactersListScreenRoot
import kotlinx.serialization.Serializable
import myapplication.shared.generated.resources.Res
import myapplication.shared.generated.resources.details_screen
import myapplication.shared.generated.resources.list_screen
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.charactersGraph(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
    onAction: (Actions) -> Unit
) {

    composable<CharactersGraph.InnerGraph.ListScreen> {
        CharactersListScreenRoot(
            navController,
            sharedTransitionScope,
            this@composable
        )
        onAction(
            Actions.UpdateTopAppBarTitle(stringResource(Res.string.list_screen))
        )
    }

    composable<CharactersGraph.InnerGraph.DetailsScreen> { backStackEntry ->
        val detailsScreenRout = backStackEntry.toRoute<CharactersGraph.InnerGraph.DetailsScreen>()
        DetailsScreenRoot(
            detailsScreenRout.charName,
            detailsScreenRout.image,
            sharedTransitionScope,
            this@composable
        )
        onAction(
            Actions.UpdateTopAppBarTitle(stringResource(Res.string.details_screen))
        )
    }
}

@Serializable
data object CharactersGraph {

    @Serializable
    data object Parent

    @Serializable
    data object InnerGraph {

        @Serializable
        data object ListScreen

        @Serializable
        data class DetailsScreen(val charName: String, val image: String)
    }
}