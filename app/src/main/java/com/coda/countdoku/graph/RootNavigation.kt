package com.coda.countdoku.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coda.countdoku.presentation.home.HomeScreen
import com.coda.countdoku.presentation.splash.SplashScreen

@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Screen.SPLASH.route
    ) {
        composable(route = Screen.SPLASH.route) {
            SplashScreen(
                modifier = Modifier,
            )
        }
        composable(route = Screen.HOME.route) {
            HomeScreen(navController = navController)
        }
    }
}

object Graph {
    const val ROOT = "root_graph"
}