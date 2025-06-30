package com.rr.adone.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.rr.adone.navigation.NavigationDestination
import com.rr.adone.ui.screens.detail.DetailScreen
import com.rr.adone.ui.screens.home.HomeScreen
import com.rr.adone.ui.screens.learning.LearningScreen
import com.rr.adone.ui.screens.search.SearchScreen
import com.rr.adone.ui.screens.settings.SettingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    val bottomBarRoutes = listOf(
        NavigationDestination.Home,
        NavigationDestination.Search,
        NavigationDestination.Settings
    )
    
    val shouldShowBottomBar = bottomBarRoutes.any { route ->
        currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
    }
    
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowBottomBar) {
                NavigationBar {
                    bottomBarDestinations.forEach { destination ->
                        NavigationBarItem(
                            icon = { Icon(destination.icon, contentDescription = destination.label) },
                            label = { Text(destination.label) },
                            selected = currentDestination?.hierarchy?.any { 
                                it.hasRoute(destination.route::class) 
                            } == true,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(NavigationDestination.Home) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavigationDestination.Home,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<NavigationDestination.Home> {
                HomeScreen(
                    onNavigateToDetail = { contentId ->
                        navController.navigate(NavigationDestination.Detail(contentId))
                    },
                    onNavigateToSearch = {
                        navController.navigate(NavigationDestination.Search)
                    }
                )
            }
            
            composable<NavigationDestination.Search> {
                SearchScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToDetail = { contentId ->
                        navController.navigate(NavigationDestination.Detail(contentId))
                    }
                )
            }
            
            composable<NavigationDestination.Detail> { backStackEntry ->
                val detail: NavigationDestination.Detail = backStackEntry.toRoute()
                DetailScreen(
                    contentId = detail.contentId,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onStartLearning = { contentId ->
                        navController.navigate(NavigationDestination.Learning(contentId))
                    }
                )
            }
            
            composable<NavigationDestination.Learning> { backStackEntry ->
                val learning: NavigationDestination.Learning = backStackEntry.toRoute()
                LearningScreen(
                    contentId = learning.contentId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable<NavigationDestination.Settings> {
                SettingsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

private data class BottomBarDestination(
    val route: NavigationDestination,
    val icon: ImageVector,
    val label: String
)

private val bottomBarDestinations = listOf(
    BottomBarDestination(
        route = NavigationDestination.Home,
        icon = Icons.Default.Home,
        label = "Home"
    ),
    BottomBarDestination(
        route = NavigationDestination.Search,
        icon = Icons.Default.Search,
        label = "Search"
    ),
    BottomBarDestination(
        route = NavigationDestination.Settings,
        icon = Icons.Default.Settings,
        label = "Settings"
    )
)