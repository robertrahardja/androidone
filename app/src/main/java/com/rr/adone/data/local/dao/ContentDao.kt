package com.rr.adone.data.local.dao

import androidx.room.*
import com.rr.adone.domain.model.ContentItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ContentDao {
    
    @Query("SELECT * FROM content_items ORDER BY title ASC")
    fun getAllContentFlow(): Flow<List<ContentItem>>
    
    @Query("SELECT * FROM content_items ORDER BY title ASC")
    suspend fun getAllContent(): List<ContentItem>
    
    @Query("SELECT * FROM content_items WHERE id = :id")
    suspend fun getContentById(id: String): ContentItem?
    
    @Query("SELECT * FROM content_items WHERE title LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchContent(query: String): Flow<List<ContentItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(item: ContentItem)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContentItems(items: List<ContentItem>)
    
    @Update
    suspend fun updateContent(item: ContentItem)
    
    @Delete
    suspend fun deleteContent(item: ContentItem)
    
    @Query("DELETE FROM content_items")
    suspend fun deleteAllContent()
    
    @Query("SELECT COUNT(*) FROM content_items")
    suspend fun getContentCount(): Int
}