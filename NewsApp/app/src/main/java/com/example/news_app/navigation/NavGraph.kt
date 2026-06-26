package com.example.news_app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.news_app.ui.components.NewsBottomNavBar
import com.example.news_app.ui.screens.DetailScreen
import com.example.news_app.ui.screens.HomeScreen
import com.example.news_app.ui.screens.SearchScreen
import com.example.news_app.ui.theme.NewsPurple
import com.example.news_app.viewmodel.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavGraph() {
    val navController: NavHostController = rememberNavController()
    val viewModel = viewModel<NewsViewModel>()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showTopLevelBars = currentRoute == "home" || currentRoute == "search"

    Scaffold(
        topBar = {
            if (showTopLevelBars) {
                TopAppBar(
                    title = { Text("News App") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = NewsPurple,
                        titleContentColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            if (showTopLevelBars) {
                NewsBottomNavBar(
                    currentRoute = currentRoute,
                    onNavigate = { route ->
                        navController.navigate(route) {
                            launchSingleTop = true
                            popUpTo("home")
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen(viewModel = viewModel) { article ->
                    viewModel.selectArticle(article)
                    navController.navigate("detail")
                }
            }

            composable("search") {
                SearchScreen(viewModel = viewModel) { article ->
                    viewModel.selectArticle(article)
                    navController.navigate("detail")
                }
            }

            composable("detail") {
                val article by viewModel.selectedArticle.collectAsState()
                article?.let {
                    DetailScreen(
                        article = it,
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}
