package com.rr.adone.domain.repository

import com.rr.adone.domain.model.ContentItem
import kotlinx.coroutines.flow.Flow

interface ContentRepository {
    fun getContentItemsFlow(): Flow<List<ContentItem>>
    suspend fun getContentItems(): List<ContentItem>
    suspend fun getContentItem(id: String): ContentItem?
    fun searchContent(query: String): Flow<List<ContentItem>>
    suspend fun refreshContentItems()
    suspend fun createContentItem(item: ContentItem): ContentItem
    suspend fun updateContentItem(item: ContentItem): ContentItem
    suspend fun deleteContentItem(id: String): Boolean
}