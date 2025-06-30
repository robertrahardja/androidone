package com.rr.adone.domain.usecase

import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContentItemsUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    operator fun invoke(): Flow<List<ContentItem>> {
        return contentRepository.getContentItemsFlow()
    }
}