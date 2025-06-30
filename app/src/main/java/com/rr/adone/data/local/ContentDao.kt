package com.rr.adone.data.local

import androidx.room.*
import com.rr.adone.domain.model.ContentItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    @Query("SELECT * FROM content_items ORDER BY title ASC")
    fun getAllContentItems(): Flow<List<ContentItem>>

    @Query("SELECT * FROM content_items WHERE id = :id")
    suspend fun getContentItemById(id: String): ContentItem?

    @Query("SELECT * FROM content_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%'")
    fun searchContentItems(query: String): Flow<List<ContentItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentItems(items: List<ContentItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentItem(item: ContentItem)

    @Delete
    suspend fun deleteContentItem(item: ContentItem)

    @Query("DELETE FROM content_items")
    suspend fun deleteAllContentItems()
}