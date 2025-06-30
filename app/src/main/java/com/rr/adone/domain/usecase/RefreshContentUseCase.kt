package com.rr.adone.domain.usecase

import com.rr.adone.domain.repository.ContentRepository
import javax.inject.Inject

class RefreshContentUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {
    suspend operator fun invoke() {
        contentRepository.refreshContentItems()
    }
}