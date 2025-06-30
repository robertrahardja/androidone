package com.rr.adone.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rr.adone.domain.usecase.GetThemePreferencesUseCase

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val systemInDarkTheme = isSystemInDarkTheme()
    
    // Create a ViewModel to observe theme preferences
    val themeViewModel: AppThemeViewModel = hiltViewModel()
    val isDarkMode by themeViewModel.isDarkMode.collectAsState(initial = systemInDarkTheme)
    
    AdoneTheme(
        darkTheme = isDarkMode,
        content = content
    )
}

// Simple ViewModel to provide theme state
@dagger.hilt.android.lifecycle.HiltViewModel
class AppThemeViewModel @javax.inject.Inject constructor(
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase
) : androidx.lifecycle.ViewModel() {
    
    val isDarkMode = getThemePreferencesUseCase.isDarkMode()
}