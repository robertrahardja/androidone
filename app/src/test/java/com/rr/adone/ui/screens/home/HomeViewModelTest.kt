package com.rr.adone.ui.screens.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.usecase.GetContentItemsUseCase
import com.rr.adone.domain.usecase.RefreshContentUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()

    @Mock
    private lateinit var getContentItemsUseCase: GetContentItemsUseCase

    @Mock
    private lateinit var refreshContentUseCase: RefreshContentUseCase

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be loading`() = runTest {
        // Given
        val items = listOf(
            ContentItem("1", "Title 1", "Description 1"),
            ContentItem("2", "Title 2", "Description 2")
        )
        whenever(getContentItemsUseCase()).thenReturn(flowOf(items))

        // When
        viewModel = HomeViewModel(getContentItemsUseCase, refreshContentUseCase)

        // Then
        val finalState = viewModel.uiState.value
        assertEquals(items, finalState.items)
        assertFalse(finalState.isLoading)
        assertEquals(null, finalState.error)
    }

    @Test
    fun `onRefresh should update loading state`() = runTest {
        // Given
        whenever(getContentItemsUseCase()).thenReturn(flowOf(emptyList()))
        viewModel = HomeViewModel(getContentItemsUseCase, refreshContentUseCase)

        // When
        viewModel.onRefresh()

        // Then
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `onErrorDismissed should clear error`() = runTest {
        // Given
        whenever(getContentItemsUseCase()).thenReturn(flowOf(emptyList()))
        viewModel = HomeViewModel(getContentItemsUseCase, refreshContentUseCase)

        // When
        viewModel.onErrorDismissed()

        // Then
        assertEquals(null, viewModel.uiState.value.error)
    }
}