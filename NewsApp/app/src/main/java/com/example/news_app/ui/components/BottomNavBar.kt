package com.example.news_app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.news_app.ui.theme.NewsBottomBarBackground
import com.example.news_app.ui.theme.NewsPurple
import com.example.news_app.ui.theme.NewsPurpleLight
import com.example.news_app.ui.theme.NewsTextSecondary

private data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem("home", "Home", Icons.Filled.Home),
    BottomNavItem("search", "Search", Icons.Filled.Search)
)

@Composable
fun NewsBottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar(containerColor = NewsBottomBarBackground) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = NewsPurple,
                    selectedTextColor = NewsPurple,
                    indicatorColor = NewsPurpleLight,
                    unselectedIconColor = NewsTextSecondary,
                    unselectedTextColor = NewsTextSecondary
                )
            )
        }
    }
}
