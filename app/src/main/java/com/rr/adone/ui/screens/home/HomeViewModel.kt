package com.rr.adone.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.usecase.GetContentItemsUseCase
import com.rr.adone.domain.usecase.RefreshContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getContentItemsUseCase: GetContentItemsUseCase,
    private val refreshContentUseCase: RefreshContentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadContent()
        // Initialize data if needed
        onRefresh()
    }

    private fun loadContent() {
        viewModelScope.launch {
            getContentItemsUseCase()
                .onStart { 
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
                .catch { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
                .collect { items ->
                    _uiState.value = _uiState.value.copy(
                        items = items,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            try {
                refreshContentUseCase()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Failed to refresh content"
                )
            } finally {
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            }
        }
    }

    fun onItemClick(item: ContentItem) {
        // Navigation will be handled by the UI layer
    }

    fun onErrorDismissed() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}