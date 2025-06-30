package com.rr.adone.data.repository

import com.rr.adone.data.local.dao.ContentDao
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentRepositoryImpl @Inject constructor(
    private val contentDao: ContentDao
) : ContentRepository {

    override fun getContentItemsFlow(): Flow<List<ContentItem>> {
        return contentDao.getAllContentFlow()
    }

    override suspend fun getContentItems(): List<ContentItem> {
        return contentDao.getAllContent()
    }

    override suspend fun getContentItem(id: String): ContentItem? {
        return contentDao.getContentById(id)
    }

    override fun searchContent(query: String): Flow<List<ContentItem>> {
        return contentDao.searchContent(query)
    }

    override suspend fun refreshContentItems() {
        // If database is empty, populate with sample data
        if (contentDao.getContentCount() == 0) {
            insertSampleData()
        }
    }

    override suspend fun createContentItem(item: ContentItem): ContentItem {
        contentDao.insertContent(item)
        return item
    }

    override suspend fun updateContentItem(item: ContentItem): ContentItem {
        contentDao.updateContent(item)
        return item
    }

    override suspend fun deleteContentItem(id: String): Boolean {
        val item = contentDao.getContentById(id)
        return if (item != null) {
            contentDao.deleteContent(item)
            true
        } else {
            false
        }
    }

    private suspend fun insertSampleData() {
        val sampleItems = listOf(
            ContentItem(
                id = "1",
                title = "Modern Android Development",
                description = "Learn the latest Android development practices with Jetpack Compose, MVVM architecture, and modern tools for building professional apps.",
                category = "Architecture",
                difficulty = "Intermediate",
                estimatedTime = "45 min"
            ),
            ContentItem(
                id = "2",
                title = "Kotlin Fundamentals",
                description = "Master Kotlin programming language features including coroutines, extension functions, and null safety for Android development.",
                category = "Programming",
                difficulty = "Beginner",
                estimatedTime = "60 min"
            ),
            ContentItem(
                id = "3",
                title = "Jetpack Compose UI",
                description = "Build beautiful, declarative user interfaces with Jetpack Compose. Learn state management, animations, and Material 3 design.",
                category = "UI/UX",
                difficulty = "Intermediate",
                estimatedTime = "90 min"
            ),
            ContentItem(
                id = "4",
                title = "MVVM Architecture Pattern",
                description = "Implement Model-View-ViewModel architecture with StateFlow, ViewModels, and clean separation of concerns.",
                category = "Architecture",
                difficulty = "Advanced",
                estimatedTime = "75 min"
            ),
            ContentItem(
                id = "5",
                title = "Room Database & Offline-First",
                description = "Create robust local storage with Room database, implement offline-first architecture, and sync with remote APIs.",
                category = "Data Management",
                difficulty = "Intermediate",
                estimatedTime = "120 min"
            ),
            ContentItem(
                id = "6",
                title = "Dependency Injection with Hilt",
                description = "Simplify dependency management using Hilt. Learn about scopes, modules, and testing with dependency injection.",
                category = "Architecture",
                difficulty = "Advanced",
                estimatedTime = "90 min"
            ),
            ContentItem(
                id = "7",
                title = "Ktor Client & Networking",
                description = "Implement modern HTTP networking with Ktor client, handle API responses, and manage network errors gracefully.",
                category = "Networking",
                difficulty = "Intermediate",
                estimatedTime = "60 min"
            ),
            ContentItem(
                id = "8",
                title = "Testing Strategies",
                description = "Write comprehensive tests including unit tests, UI tests, and integration tests. Learn TDD principles for Android.",
                category = "Testing",
                difficulty = "Advanced",
                estimatedTime = "150 min"
            ),
            ContentItem(
                id = "9",
                title = "Material 3 Design System",
                description = "Implement Google's latest Material Design 3 with dynamic colors, typography, and component guidelines.",
                category = "UI/UX",
                difficulty = "Beginner",
                estimatedTime = "45 min"
            ),
            ContentItem(
                id = "10",
                title = "Navigation Component",
                description = "Master type-safe navigation in Compose, handle deep links, and implement complex navigation flows.",
                category = "Navigation",
                difficulty = "Intermediate",
                estimatedTime = "75 min"
            ),
            ContentItem(
                id = "11",
                title = "Performance Optimization",
                description = "Optimize app performance with proper state management, lazy loading, and efficient Compose recomposition.",
                category = "Performance",
                difficulty = "Advanced",
                estimatedTime = "90 min"
            ),
            ContentItem(
                id = "12",
                title = "Security Best Practices",
                description = "Implement app security with encrypted storage, certificate pinning, and secure API communication.",
                category = "Security",
                difficulty = "Advanced",
                estimatedTime = "105 min"
            )
        )
        contentDao.insertContentItems(sampleItems)
    }
}