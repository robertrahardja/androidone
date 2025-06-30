package com.rr.adone.domain.usecase

import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchContentUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    operator fun invoke(query: String): Flow<List<ContentItem>> {
        return if (query.isBlank()) {
            contentRepository.getContentItemsFlow()
        } else {
            contentRepository.searchContent(query)
        }
    }
}