package com.myapplication.pressentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.myapplication.pressentation.characters.CharactersGraph
import com.myapplication.pressentation.characters.CharactersParentRoot
import com.myapplication.pressentation.welcome.WelcomeGraph
import com.myapplication.pressentation.welcome.WelcomeScreenRoot
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {

            val navController = rememberNavController()

            AppLaunchNavigationStack(navController)
        }
    }
}

@Composable
private fun AppLaunchNavigationStack(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = WelcomeGraph.WelcomeScreen
    ) {
        composable<WelcomeGraph.WelcomeScreen> {
            WelcomeScreenRoot(navController)
        }

        composable<CharactersGraph.Parent> {
            CharactersParentRoot()
        }
    }
}