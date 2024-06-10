package com.reggya.newsapp.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reggya.newsapp.ui.detail.DetailScreen
import com.reggya.newsapp.ui.home.HomeScreen
import com.reggya.newsapp.ui.viewmodel.NewsViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route,
    ) {

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(
//                newsViewModel = newsViewModel,
                navController = navController
            )
        }

        composable(route = Screen.DetailScreen.route) {
            DetailScreen()
        }
    }
}
