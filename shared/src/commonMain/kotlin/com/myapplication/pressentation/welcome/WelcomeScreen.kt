package com.myapplication.pressentation.welcome

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.myapplication.pressentation.characters.CharactersGraph
import kotlinx.coroutines.delay
import myapplication.shared.generated.resources.Res
import myapplication.shared.generated.resources.welcome
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Duration.Companion.seconds

@Composable
fun WelcomeScreenRoot(navController: NavHostController) {

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {

        LaunchedEffect(key1 = true) {
            delay(2.seconds)
            navController.navigate(CharactersGraph.Parent) {
                popUpTo(WelcomeGraph.WelcomeScreen) { inclusive = true }
            }
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(Res.string.welcome)
        )

    }
}