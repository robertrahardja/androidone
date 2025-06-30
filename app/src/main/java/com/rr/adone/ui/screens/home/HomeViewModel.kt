package com.rr.adone.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rr.adone.domain.model.ContentItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadSampleData()
    }

    private fun loadSampleData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // Sample data for demonstration
            val sampleItems = listOf(
                ContentItem("1", "Android Development", "Learn modern Android development with Jetpack Compose"),
                ContentItem("2", "Kotlin Programming", "Master Kotlin for Android development"),
                ContentItem("3", "Material Design", "Implement beautiful Material 3 designs"),
                ContentItem("4", "Architecture Patterns", "MVVM and Clean Architecture best practices"),
                ContentItem("5", "Testing", "Unit and UI testing strategies")
            )

            _uiState.value = _uiState.value.copy(
                items = sampleItems,
                isLoading = false
            )
        }
    }

    fun onItemClick(item: ContentItem) {
        // Handle item click - navigate to detail screen, etc.
        // This is where you would typically navigate to a detail screen
    }
}