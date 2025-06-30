package com.rr.adone.domain.usecase

import com.rr.adone.data.preferences.ThemePreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemePreferencesUseCase @Inject constructor(
    private val themePreferences: ThemePreferences
) {
    fun isDarkMode(): Flow<Boolean> = themePreferences.isDarkMode
    
    fun notificationsEnabled(): Flow<Boolean> = themePreferences.notificationsEnabled
    
    suspend fun setDarkMode(enabled: Boolean) {
        themePreferences.setDarkMode(enabled)
    }
    
    suspend fun setNotificationsEnabled(enabled: Boolean) {
        themePreferences.setNotificationsEnabled(enabled)
    }
}