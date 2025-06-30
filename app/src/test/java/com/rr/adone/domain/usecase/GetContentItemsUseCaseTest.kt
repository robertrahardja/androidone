package com.rr.adone.domain.usecase

import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

class GetContentItemsUseCaseTest {

    @Mock
    private lateinit var contentRepository: ContentRepository

    private lateinit var getContentItemsUseCase: GetContentItemsUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getContentItemsUseCase = GetContentItemsUseCase(contentRepository)
    }

    @Test
    fun `invoke should return content items from repository`() = runTest {
        // Given
        val expectedItems = listOf(
            ContentItem("1", "Title 1", "Description 1"),
            ContentItem("2", "Title 2", "Description 2")
        )
        whenever(contentRepository.getContentItemsFlow()).thenReturn(flowOf(expectedItems))

        // When
        val result = getContentItemsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(expectedItems, result[0])
    }

    @Test
    fun `invoke should return empty list when repository is empty`() = runTest {
        // Given
        whenever(contentRepository.getContentItemsFlow()).thenReturn(flowOf(emptyList()))

        // When
        val result = getContentItemsUseCase().toList()

        // Then
        assertEquals(1, result.size)
        assertEquals(emptyList<ContentItem>(), result[0])
    }
}