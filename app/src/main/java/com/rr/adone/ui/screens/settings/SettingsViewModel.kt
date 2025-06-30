package com.rr.adone.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rr.adone.domain.usecase.GetThemePreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemePreferencesUseCase: GetThemePreferencesUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
    
    init {
        viewModelScope.launch {
            combine(
                getThemePreferencesUseCase.isDarkMode(),
                getThemePreferencesUseCase.notificationsEnabled()
            ) { isDarkMode, notificationsEnabled ->
                SettingsUiState(
                    isDarkMode = isDarkMode,
                    notificationsEnabled = notificationsEnabled
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
    
    fun toggleDarkMode() {
        viewModelScope.launch {
            getThemePreferencesUseCase.setDarkMode(!_uiState.value.isDarkMode)
        }
    }
    
    fun toggleNotifications() {
        viewModelScope.launch {
            getThemePreferencesUseCase.setNotificationsEnabled(!_uiState.value.notificationsEnabled)
        }
    }
}

data class SettingsUiState(
    val isDarkMode: Boolean = false,
    val notificationsEnabled: Boolean = true
)