package com.rr.adone.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class NavigationDestination {
    @Serializable
    data object Home : NavigationDestination()
    
    @Serializable
    data object Search : NavigationDestination()
    
    @Serializable
    data class Detail(val contentId: String) : NavigationDestination()
    
    @Serializable
    data class Learning(val contentId: String) : NavigationDestination()
    
    @Serializable
    data object Settings : NavigationDestination()
}