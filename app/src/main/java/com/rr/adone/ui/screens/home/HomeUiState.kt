package com.rr.adone.ui.screens.home

import com.rr.adone.domain.model.ContentItem

data class HomeUiState(
    val items: List<ContentItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)