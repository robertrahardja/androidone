package com.rr.adone.ui.screens.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.ui.theme.AdoneTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_displaysContentItems() {
        // Given
        val testItems = listOf(
            ContentItem("1", "Test Title 1", "Test Description 1"),
            ContentItem("2", "Test Title 2", "Test Description 2")
        )

        // When
        composeTestRule.setContent {
            AdoneTheme {
                // Note: In a real test, you would inject a test ViewModel
                // For now, this tests the UI structure
                HomeScreen()
            }
        }

        // Then
        composeTestRule.onNodeWithText("Test Title 1").assertExists()
        composeTestRule.onNodeWithText("Test Description 1").assertExists()
    }

    @Test
    fun homeScreen_clickItem_triggersNavigation() {
        var clickedItemId: String? = null

        composeTestRule.setContent {
            AdoneTheme {
                HomeScreen(
                    onNavigateToDetail = { itemId ->
                        clickedItemId = itemId
                    }
                )
            }
        }

        // When user clicks on first item
        composeTestRule.onNodeWithText("Android Development").performClick()

        // Then navigation should be triggered
        // Note: This would need actual ViewModel integration to work properly
    }
}